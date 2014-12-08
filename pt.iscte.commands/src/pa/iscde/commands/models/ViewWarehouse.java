package pa.iscde.commands.models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.controllers.ExtensionHandler;
import pa.iscde.commands.controllers.ViewExtensionHandler;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pt.iscte.pidesco.extensibility.PidescoServices;

final public class ViewWarehouse {

	private Set<String> viewsWarehouse;
	private static ViewWarehouse instance;

	public static ViewWarehouse getInstance() {
		if (instance == null)
			instance = new ViewWarehouse();

		return instance;
	}

	private ViewWarehouse() {
		viewsWarehouse = new HashSet<String>();
	}

	public boolean containsKey(String id) {
		return viewsWarehouse.contains(id);
	}

	// This Method can only be when there are already WorkbenchPages and stuff
	// related to the UI
	// It returns a List of Strings with all Opened Views at the Moment
	public List<String> loadAllViewsFromExtensionsPoints() {
		List<String> activeViews = new LinkedList<String>();

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewReference[] listView = page.getViewReferences();

		for (IViewReference view : listView)
			activeViews.add(view.getSecondaryId());

		return activeViews;
	}

	// This method is used to load all PidescoViews being used at
	// ExtensionPoints in the system
	public void loadAllViews() {
		ExtensionHandler handler = ExtensionHandler.getInstance();
		handler.setExtensionHandler(ExtensionPointsIDS.VIEW_ID.getID(),
				new ViewExtensionHandler(viewsWarehouse));
		handler.startProcessExtension();
	}

	public static String getActiveView() {
		BundleContext context = CommandsController.getContext();
		ServiceReference<PidescoServices> ref = context
				.getServiceReference(PidescoServices.class);
		PidescoServices services = context.getService(ref);
		return services.getActiveView();
	}

	// Called when the Plug-in is destroyed (this needs to be done, in case to
	// of re-launch the plug-in in run-time)
	public static void eraseModel() {
		instance = null;
	}

}
