package pa.iscde.commands.controllers;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import pa.iscde.commands.controllers.KeyPressDetector.KeyStrokeListener;
import pa.iscde.commands.features.search.SearchWord;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.utils.ExtensionPointsIDS;

public class CommandsController implements BundleActivator {

	private static BundleContext context;
	private KeyListener listener;

	public static BundleContext getContext() {
		return CommandsController.context;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		CommandsController.context = context;

		ExtensionHandler handler = ExtensionHandler.getInstance();
		handler.setExtensionHandler(ExtensionPointsIDS.COMMAND_ID.getID(),
				new CommandHandler());
		handler.startProcessExtension();

		listener = new KeyListener();
		KeyPressDetector.getInstance().addKeyPressListener(listener);

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		KeyPressDetector.getInstance().removeKeyPressListener(listener);
		CommandWarehouse.clearAll();
	}

	private final class KeyListener implements KeyStrokeListener {
		@Override
		public void keyPressed(CommandKey c) {
			CommandDefinition commandDefinitionTyped = CommandWarehouse
					.getCommandDefinition(c);

			System.out.println(" -- ");
			System.out
					.println("CommandsController.start(...).new KeyStrokeListener() {...}.keyPressed()");
			CommandWarehouse.printall();
			System.out.println(" -- ");

			if (commandDefinitionTyped != null) {
				// This blocks means that the key typed is
				// registered
				commandDefinitionTyped.getCommand().action();
			} else {
				// This line must be deleted, is just here for
				// debugging
				System.out
						.println("The key is not registered in the database! Or maybe wrong context!");
			}
		}
	}

}
