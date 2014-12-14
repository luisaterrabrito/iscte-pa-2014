package pa.iscde.commands.features.actions;

import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.services.Action;
import pa.iscde.commands.services.CommandDefinition;

public class DeleteCommand implements Action {

	@Override
	public void action(CommandDataAdaptor data) {

		for (CommandDefinition command : data.getSelectedCommands()) {
			data.deleteCommandLine(command.getCommandKey());
		}

	}

}
