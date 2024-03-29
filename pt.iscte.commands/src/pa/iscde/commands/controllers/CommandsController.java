package pa.iscde.commands.controllers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.models.CommandServicesImpl;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.models.ViewWarehouse;
import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;
import pa.iscde.commands.services.CommandServices;
import pa.iscde.commands.utils.ExtensionPointsIDS;

public class CommandsController implements BundleActivator {

	private static BundleContext context;
	private CommandKeyListener commandKeylistener = new CommandKeyListener() {

		@Override
		public boolean keyPressed(Event event) {
			boolean commandKeyDetected = super.keyPressed(event);

			if (commandKeyDetected) {

				
				boolean ctrl_clicked = (event.stateMask & SWT.CTRL) == SWT.CTRL;
				boolean alt_clicked = (event.stateMask & SWT.ALT) == SWT.ALT;
				int keyCode_lastKey = event.keyCode;

				String viewActive = ViewWarehouse.getActiveView();

				CommandKey typed = new CommandKey(viewActive, ctrl_clicked,
						alt_clicked, (char) keyCode_lastKey);
				CommandDefinition commandDefinitionTyped = CommandWarehouse.getInstance().getCommandDefinition(typed);
				
				
				if (commandDefinitionTyped != null) {
					// This blocks means that the key typed is registered
					commandDefinitionTyped.getCommand().action();
					return true;
				} else {
					// This line must be deleted, is just here for debugging
					System.out
							.println("The key is not registered in the database! Or maybe wrong context!");
				}
			}
			return false;
		}

	};

	public static BundleContext getContext() {
		return CommandsController.context;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		CommandsController.context = context;
		ViewWarehouse.getInstance().loadAllViews();

		ExtensionHandler handler = ExtensionHandler.getInstance();
		handler.setExtensionHandler(ExtensionPointsIDS.COMMAND_ID.getID(),
				new CommandExtensionHandler());
		handler.startProcessExtension();

		KeyPressDetector.getInstance().addKeyPressListener(commandKeylistener);
		
		CommandServices commandServices = new CommandServicesImpl(new CommandDataAdaptor(null));
		context.registerService(CommandServices.class, commandServices, null);
		
		

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		ViewWarehouse.eraseModel();
		CommandWarehouse.eraseModel();
		ExtensionHandler.destroyInstance();

		KeyPressDetector.getInstance().removeKeyPressListener(
				commandKeylistener);

		KeyPressDetector.destroyInstance();

	}

}
