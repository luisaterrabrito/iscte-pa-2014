package pt.iscte.pidesco.internal;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISizeProvider;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import pt.iscte.pidesco.extensibility.PidescoExtensionPoint;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoTool;
import pt.iscte.pidesco.internal.PidescoActivator.ViewComponent;

public class AbstractPidescoView extends ViewPart implements ISizeProvider {

	private static String IMAGES_FLODER_PATH = PidescoServices.IMAGES_FOLDER + "/";
	
	private ViewComponent vcomponent;
	private Composite parent;
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		vcomponent = PidescoActivator.getInstance().getComponent(site.getSecondaryId());
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		setPartName(vcomponent.viewTitle);
		if(vcomponent.icon != null)
			setTitleImage(vcomponent.icon.createImage());
		setTitleToolTip("Contributor: " + vcomponent.pluginId);
		parent.setLayout(new FillLayout());
		Map<String, Image> imagesMap = Collections.unmodifiableMap(buildImageMap());
		vcomponent.createContents(parent, imagesMap);
	}
	
	@Override
	public void setFocus() {
		parent.setFocus();
	}
	

	private Map<String, Image> buildImageMap() {
		Map<String, Image> imagesMap = new HashMap<String, Image>();
		Bundle bundle = Platform.getBundle(vcomponent.pluginId);
		Enumeration<String> imgs = bundle.getEntryPaths(IMAGES_FLODER_PATH);
		if(imgs != null) {
			while(imgs.hasMoreElements()) {
				String path = imgs.nextElement();
				String key = path.substring(IMAGES_FLODER_PATH.length());
				try {
					ImageDescriptor desc = PidescoActivator.imageDescriptorFromPlugin(vcomponent.pluginId, path);
					if(desc != null) {
						imagesMap.put(key, desc.createImage());
					}
				}
				catch(SWTException e) {

				}
			}
		}
		return imagesMap;
	}



	private void fillLocalToolBar(IToolBarManager manager) {
		for(IExtension ext : PidescoExtensionPoint.TOOL.getExtensions()) {
			IConfigurationElement c = ext.getConfigurationElements()[0];

			if(c.getAttribute("view").equals(vcomponent.viewId)) {
				String contributor = ext.getContributor().getName();
				final String toolName = ext.getLabel();
				try {
					final PidescoTool tool = (PidescoTool) c.createExecutableExtension("class");
					boolean toggle = Boolean.parseBoolean(c.getAttribute("hasState"));
					final Action action = new Action(toolName, toggle ? IAction.AS_CHECK_BOX : IAction.AS_PUSH_BUTTON) {
						public void run() {
							tool.run(isChecked());
						}
					};

					action.setText(toolName);
					action.setToolTipText(c.getAttribute("description") + "\n" + "Contributor: " + contributor);
					String iconPath = IMAGES_FLODER_PATH + c.getAttribute("icon");
					ImageDescriptor icon = PidescoActivator.imageDescriptorFromPlugin(contributor, iconPath);
					if(icon != null)
						action.setImageDescriptor(icon);

					manager.add(action);
				}
				catch (CoreException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		try {
			Platform.getBundle(vcomponent.pluginId).stop();
		} catch (BundleException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int getSizeFlags(boolean width) {
		return SWT.MIN;
	}

	@Override
	public int computePreferredSize(boolean width, int availableParallel,
			int availablePerpendicular, int preferredResult) {
		System.out.println(availableParallel + " " + availablePerpendicular);
		return 200;
	}
	
}
