package pa.iscde.commands.features.actions;

import org.eclipse.swt.widgets.Display;
import pa.iscde.commands.internal.services.Action;
import pa.iscde.commands.models.CommandDataAdaptor;


public class NewCommand implements Action {

	private NewCommandDialog newCommand;

	@Override
	public void action(CommandDataAdaptor data) {

		newCommand = new NewCommandDialog(Display.getCurrent().getActiveShell());
		newCommand.open();

		// CommandKey cmdKey = new CommandKey("comando teste",
		// "pa.iscde.commands.CommandsView", true, false, 'X');
		// CommandDefinition cmdDef = new CommandDefinition(cmdKey,
		// "pa.iscde.commands.CommandsView", new CommentFeature(),
		// "novo comando por via dinâmica");
		//
		// data.insertCommandLine(cmdDef);

	}

}
