package pa.iscde.commands.trvms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.commands.services.*;
import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;


public class CommandsSearch implements SearchProvider {

	@Override
	public List<Object> getResults(String text) {
		
		CommandServices commandsService;
		
		BundleContext context = FrameworkUtil.getBundle(CommandsSearch.class).getBundleContext();
		ServiceReference<CommandServices> ref = context.getServiceReference(CommandServices.class);
		commandsService = context.getService(ref);
		
		return new LinkedList<Object>(Arrays.asList(commandsService.getFilteredCommands(text).toArray()));
		
	}

	@Override
	public Image setImage(Object object) {

		CommandDefinition o = null;
		
		if(object instanceof CommandDefinition)
			o = (CommandDefinition) object;
		
		
		BundleContext context = FrameworkUtil.getBundle(CommandsSearch.class).getBundleContext();
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		PidescoServices pidescoServices = context.getService(serviceReference_pidesco);
		
		
		CommandServices commandsService;
		ServiceReference<CommandServices> ref = context.getServiceReference(CommandServices.class);
		commandsService = context.getService(ref);
		
		ViewDef v = commandsService.getViewDefFromUniqueIdentifier(o.getContext());
		
		if(v.getImageIdentifier() == null)
			return null;
		
		return pidescoServices.getImageFromPlugin(v.getPluginContributor(), v.getImageIdentifier());
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {

		
		CommandServices commandsService;
		BundleContext context = FrameworkUtil.getBundle(CommandsSearch.class).getBundleContext();
		ServiceReference<CommandServices> ref = context.getServiceReference(CommandServices.class);
		commandsService = context.getService(ref);
		
		if(object instanceof CommandDefinition){
			boolean result = commandsService.requestBindingEdition((CommandDefinition) object);
			if(result != true)
				System.out.println("Error: there was a problem changing the binding "
						+ "of the command definition you clicked");
			
			if(result)
				tree.refresh();
						
		}else{
			System.err.println("Error: changing command definition binding, the method "
					+ "'doubleClickAction of interface SearchProvider is giving a bad instance of the object'");
		}
		

	}

}
