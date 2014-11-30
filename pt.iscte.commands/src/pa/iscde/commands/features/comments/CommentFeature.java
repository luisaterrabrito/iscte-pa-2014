package pa.iscde.commands.features.comments;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import pa.iscde.commands.internal.services.Command;


public class CommentFeature implements Command {

	
	
	@Override
	public void action() {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		System.out.println("The view I clicked: " + page.getActivePartReference().getPartProperty("viewid"));
		
	}

}
