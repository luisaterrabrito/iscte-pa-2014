package pa.iscde.commands.controllers;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import pa.iscde.commands.controllers.KeyPressDetector.KeyStrokeListener;
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
		
		
		KeyPressDetector.getInstance().addKeyPressListener(new KeyStrokeListener() {
			
			@Override
			public void keyPressed(CommandKey c) {
				CommandDefinition commandDefinitionTyped = CommandWarehouse.getCommandDefinition(c);

				if(commandDefinitionTyped != null){
					//This blocks means that the key typed is registered
					commandDefinitionTyped.getCommand().action();
				}else{
					//This line must be deleted, is just here for debugging
					System.out.println("The key is not registered in the database! Or maybe wrong context!");
				}
				
			}
		});
		
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {

	}

}
