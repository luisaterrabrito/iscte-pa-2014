package pa.iscde.commands.features.actions;

import pa.iscde.commands.internal.services.Action;
import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.models.CommandKey;

public class DeleteCommand implements Action {

	@Override
	public void action(CommandDataAdaptor data) {
		
		for(CommandKey command : data.getSelectedCommands()){
			data.deleteCommandLine(command);
		}
		

	}

}
