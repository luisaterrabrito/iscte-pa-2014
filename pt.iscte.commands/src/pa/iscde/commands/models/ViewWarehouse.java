package pa.iscde.commands.models;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class ViewWarehouse {

	private static Set<String> viewsWarehouse;
	
	static {
		viewsWarehouse = new HashSet<String>();
	}
	
	public static boolean containsKey(String id){
		return viewsWarehouse.contains(id);
	}

	
	//This Method can only be called here, because it's when there are already WorkbenchPages and stuff related to the UI
	public static void loadAllViews(){
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference[] listView = page.getViewReferences();
		
		for(IViewReference view : listView){
		
			if(viewsWarehouse.add(view.getSecondaryId()) == false)
				System.err.println("There is duplicated views (regarding the <pluginId>.<view class name>) in the system,"
						+ " since that some view didn't got recognised by the system. Please check the names, and give unique idenfitifers.");
			
		}
	}
	
	
	
	
}
