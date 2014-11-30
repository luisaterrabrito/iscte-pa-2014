package pa.iscde.commands.controllers;


import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.commands.internal.services.Command;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.utils.ExtensionPointsIDS;

public class CommandsController implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		loadCommandsExtensions();
		
		
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

	private void loadCommandsExtensions() {

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry.getConfigurationElementsFor(ExtensionPointsIDS.COMMAND_ID.getID());

		for (IConfigurationElement e : config) {

			try {
				
			
				String name = e.getAttribute("name");
				String ctrl_key = e.getAttribute("ctrl_key");
				String alt_key = e.getAttribute("alt_key");
				String key = e.getAttribute("key");
				String view = e.getAttribute("context_view");
				String description = e.getAttribute("description");
				Command command = (Command) e.createExecutableExtension("command_implementation");
				
				CommandKey cmdKey = new CommandKey(name, view, Boolean.valueOf(ctrl_key), Boolean.valueOf(alt_key), key.charAt(0));
				CommandDefinition replacedValue = CommandWarehouse.insertCommandDefinition(cmdKey, new CommandDefinition(cmdKey, view, command, description));
				
				if(replacedValue != null)
					System.err.println("WARNING: While loading the commands extensions, the command with the name '" + replacedValue.getCommandKey().getCommandName() + "' got replaced by the new command '"+ name + "',  if you don't want it to happen again please give unique keys combinations to your commands");
				
				
			}catch(Exception exp){
					System.err.println("ERROR: There was a error loading the commands extensions in Commands Component, "
							+ "you or some group have choosed the wrong type classes for the extensions elements.");
			}
			
			
			
		}
		



	}

	@Override
	public void stop(BundleContext context) throws Exception {


	}

}
