package pa.iscde.commands.features.search;

import org.osgi.framework.ServiceReference;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.internal.services.Command;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

final public class SearchWord implements Command {
	
	private JavaEditorServices javaServices;

	public SearchWord() {
		
		ServiceReference<JavaEditorServices> ref = CommandsController.getContext().getServiceReference(JavaEditorServices.class);
		javaServices = CommandsController.getContext().getService(ref);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

}
