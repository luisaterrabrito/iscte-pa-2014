package pt.iscte.pidesco.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import pt.iscte.pidesco.extensibility.PidescoExtensionPoint;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class PidescoServicesImpl implements PidescoServices {

	private Map<String, PidescoTool> tools;
	
	PidescoServicesImpl(BundleContext context) {
		tools = new HashMap<String, PidescoTool>();
		
		for(IExtension ext : PidescoExtensionPoint.TOOL.getExtensions()) {
			try {
				PidescoTool tool = (PidescoTool) ext.getConfigurationElements()[0].createExecutableExtension("class");
				tools.put(ext.getUniqueIdentifier(), tool);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void openView(String viewId) {
		Assert.isNotNull(viewId, "view id cannot be null");
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			page.showView(PidescoServices.VIEW_ID, viewId, IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public Image getImageFromPlugin(String pluginId, String fileName) {
		Assert.isNotNull(pluginId, "plugin id cannot be null");
		Assert.isNotNull(fileName, "file name cannot be null");
		
		ImageDescriptor imgDesc = PidescoActivator.getImageDescriptor(pluginId, IMAGES_FOLDER + "/" + fileName);
		return imgDesc == null ? null : imgDesc.createImage();
	}
	

	@Override
	public void runTool(String toolId, boolean activate) {
		Assert.isNotNull(toolId, "tool id cannot be null");
		if(!tools.containsKey(toolId))
			throw new RuntimeException("toolId '" + toolId + "' does not exist");
		
		tools.get(toolId).run(activate);
		
	}
}
