package pa.iscde.commands.models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.controllers.ExtensionHandler;
import pa.iscde.commands.controllers.ViewExtensionHandler;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pt.iscte.pidesco.extensibility.PidescoServices;

final public class ViewWarehouse {

	private Set<ViewDef> viewsWarehouse;
	private static ViewWarehouse instance;

	public static ViewWarehouse getInstance() {
		if (instance == null)
			instance = new ViewWarehouse();

		return instance;
	}

	private ViewWarehouse() {
		viewsWarehouse = new HashSet<ViewDef>();
	}

	public boolean containsKey(ViewDef id) {
		return viewsWarehouse.contains(id);
	}

	public Set<ViewDef> getViewsWarehouse() {
		return viewsWarehouse;
	}
	
	public ViewDef getViewDefFromUniqueIdentifier(String uniqueIdentifier){
		
		for(ViewDef v : viewsWarehouse){
			if(v.getUniqueIdentifier().equals(uniqueIdentifier))
				return v;
		}
		return null;
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
		
		//adiciona ainda a View do JavaEditor que não é nenhuma PidescoView
		viewsWarehouse.add(new ViewDef("pt.iscte.pidesco.javaeditor", "javafile.gif", "pt.iscte.pidesco.javaeditor"));
		
		
	}

	public static String getActiveView() {
		BundleContext context = FrameworkUtil.getBundle(CommandsController.class)
				.getBundleContext();
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
