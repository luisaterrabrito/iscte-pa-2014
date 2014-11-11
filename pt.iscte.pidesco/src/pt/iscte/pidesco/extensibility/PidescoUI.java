package pt.iscte.pidesco.extensibility;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import pt.iscte.pidesco.app.internal.AbstractPidescoView;
import pt.iscte.pidesco.app.internal.PidescoActivator;

public class PidescoUI {

	public static final String IMAGES_FOLDER = "images";

	
	public static void openView(String id) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			page.showView(AbstractPidescoView.VIEW_ID, id, IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	
	}

// under images
	public static Image getImageFromPlugin(String pluginId, String fileName) {
		ImageDescriptor imgDesc = PidescoActivator.getImageDescriptor(pluginId, IMAGES_FOLDER + "/" + fileName);
		return imgDesc == null ? null : imgDesc.createImage();
	}
}
