package pa.iscde.commands.controllers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.commands.external.services.CommandHandler;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.utils.ExtensionPointsIDS;

public class CommandsController implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		
		ExtensionHandler handler = new ExtensionHandler();
		handler.setExtensionHandler(ExtensionPointsIDS.COMMAND_ID.getID(),
				new CommandHandler());
		handler.startProcessExtension();
		
		
		Display.getDefault().addFilter(SWT.KeyDown, new Listener() {

			@Override
			public void handleEvent(Event event) {

				boolean ctrl_clicked = (event.stateMask & SWT.CTRL) == SWT.CTRL;
				boolean alt_clicked = (event.stateMask & SWT.ALT) == SWT.ALT;
				int keyCode_lastKey = event.keyCode;
				
				
				String viewActive = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
										.getActivePage().getActivePartReference().getPartProperty("viewid");
				
				
				
				//Capture any keystroke combination that uses CTRL or ALT with something else, or both and something else
				if( ((event.stateMask & SWT.CTRL) == SWT.CTRL) || ((event.stateMask & SWT.ALT) == SWT.ALT) )
				{
					if(Character.isLetterOrDigit(keyCode_lastKey) && keyCode_lastKey != SWT.CTRL &&  keyCode_lastKey != SWT.ALT){
						
						
						CommandKey typed = new CommandKey(viewActive, ctrl_clicked, alt_clicked, (char) keyCode_lastKey); 
						CommandDefinition commandDefinitionTyped = CommandWarehouse.getCommandDefinition(typed);

						if(commandDefinitionTyped != null){
							//This blocks means that the key typed is registered
							commandDefinitionTyped.getCommand().action();
						}else{
							//This line must be deleted, is just here for debugging
							System.out.println("The key is not registered in the database! Or maybe wrong context!");
						}
						
					}
				}
			}
		});
		
		
		
		


	}


	@Override
	public void stop(BundleContext context) throws Exception {


	}

}
