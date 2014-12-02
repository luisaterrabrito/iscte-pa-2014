package pt.iscte.pidesco.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

import pt.iscte.pidesco.extensibility.PidescoExtensionPoint;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.extensibility.ViewLocation;

public class PidescoActivator extends AbstractUIPlugin {

	public static final String PLUGIN_ID;
	
	static {
		PLUGIN_ID = FrameworkUtil.getBundle(PidescoActivator.class).getSymbolicName();
	}
	
	// singleton instance
	private static PidescoActivator plugin;
	
	public static PidescoActivator getInstance() {
		return plugin;
	}
	
	private PidescoServices services;
	private Map<String, ViewComponent> components;
	
	static class ViewComponent implements Comparable<ViewComponent>, PidescoView {
		final PidescoView component;
		final String pluginId;
		final String viewId;
		final String viewTitle;
		final ImageDescriptor icon;
		
		ViewComponent(PidescoView component, String pluginId, String viewId, String viewTitle, ImageDescriptor icon) {
			this.component = component;
			this.pluginId = pluginId;
			this.viewId = viewId;
			this.viewTitle = viewTitle;
			this.icon = icon;
		}
		
		static String secondaryId(String pluginId, String viewId) {
			return pluginId + "." + viewId;
		}
		
		String getSecondaryId() {
			return viewId;
		}
		
		
		@Override
		public int compareTo(ViewComponent o) {
			return viewTitle.compareTo(o.viewTitle);
		}

		@Override
		public void createContents(Composite viewArea, Map<String, Image> reg) {
			try {
				Platform.getBundle(pluginId).start();
			} catch (BundleException e) {
				e.printStackTrace();
			}
			component.createContents(viewArea, reg);
		}
	}
	
	
	
	private void loadPlugins() throws CoreException {
		components = new HashMap<String, ViewComponent>();
		
		for(IExtension viewExtension : PidescoExtensionPoint.VIEW.getExtensions()) {
			String pluginId = viewExtension.getContributor().getName();
			String viewId = viewExtension.getUniqueIdentifier();
			String viewTitle = viewExtension.getLabel();
			
			IConfigurationElement comp = viewExtension.getConfigurationElements()[0];
			PidescoView c = (PidescoView) comp.createExecutableExtension("class");
			String iconPath = PidescoServicesImpl.IMAGES_FOLDER + "/" + comp.getAttribute("icon");
			ImageDescriptor icon = null;
			if(iconPath != null && !iconPath.isEmpty()) {
				try {
					icon = imageDescriptorFromPlugin(pluginId, iconPath);
				}
				catch(RuntimeException e) {
					logPluginError(pluginId, "could not load view icon '" + iconPath + "'");
				}
			}
				
			ViewComponent vc = new ViewComponent(c, pluginId, viewId, viewTitle, icon);
			components.put(viewId, vc);
		}
	}

		
	void logPluginError(String pluginId, String message) {
		System.err.println(pluginId + ": " + message);
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		services = new PidescoServicesImpl(context);
		context.registerService(PidescoServices.class, services, null);
		loadPlugins();
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	

	ViewComponent getComponent(String viewId) {
		return components.get(viewId);
	}
	
	

	public static ImageDescriptor getImageDescriptor(String path) {
		return getImageDescriptor(PLUGIN_ID, path);
	}

	public static ImageDescriptor getImageDescriptor(String pluginId, String path) {
		return imageDescriptorFromPlugin(pluginId, path);
	}
	
	public Collection<ViewComponent> getComponents() {
		return Collections.unmodifiableCollection(components.values());
	}


	public PidescoServices getService() {
		return services;
	}


	private List<ViewLocation> viewLocations;
	
	void setLayout(List<ViewLocation> viewLocations) {
		this.viewLocations = viewLocations;
	}


	List<ViewLocation> getViewLocations() {
		if(viewLocations == null)
			return Collections.emptyList();
		else
			return viewLocations;
	}

//	private String activeViewId;
//	
//	public String getActiveViewId() {
//		return activeViewId;
//	}

//	@Override
//	public void partActivated(IWorkbenchPartReference partRef) {
//		if(partRef instanceof IViewReference)
//			activeViewId = ((IViewReference) partRef).getSecondaryId();
//		else if(partRef instanceof IEditorReference)
//			activeViewId = ((IEditorReference) partRef).getId();
//		System.out.println("activated: " + activeViewId);
//	}

	
	
}
