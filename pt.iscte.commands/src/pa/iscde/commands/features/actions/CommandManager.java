package pa.iscde.commands.features.actions;

import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.services.Action;


public class CommandManager implements Action {

	private CommandManagerDialog newCommand;

	@Override
	public void action(CommandDataAdaptor data) {

		newCommand = new CommandManagerDialog(Display.getCurrent().getActiveShell());
		newCommand.open();
	}

}
