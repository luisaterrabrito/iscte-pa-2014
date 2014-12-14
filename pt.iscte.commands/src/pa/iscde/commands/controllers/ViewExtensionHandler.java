package pa.iscde.commands.controllers;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import pa.iscde.commands.controllers.ExtensionHandler.Handler;
import pa.iscde.commands.services.ViewDef;


public class ViewExtensionHandler implements Handler {

	private Set<ViewDef> viewsInWarehouse;
	
	public ViewExtensionHandler(Set<ViewDef> viewsWarehouse) {
		this.viewsInWarehouse = viewsWarehouse;
	}

	@Override
	public void processExtension(IConfigurationElement e)
			throws CoreException {
		
		if(viewsInWarehouse.add(new ViewDef(e.getDeclaringExtension().getUniqueIdentifier(), e.getAttribute("icon"), 
				e.getDeclaringExtension().getContributor().getName()) ) == false)
			System.err.println("There is duplicated views (regarding the <pluginId>.<view class name>) in the system,"
					+ " since that some view didn't got recognised by the system. Please check the names, "
								+ "and give unique idenfitifers.");
		

	}

}
