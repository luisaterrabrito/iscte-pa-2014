package pa.iscde.commands.features.actions;

import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.internal.services.Action;
import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;

public class EditCommad implements Action {

	@Override
	public void action(CommandDataAdaptor data) {
		
		if (data.getSelectedCommands().size() == 1) {
			
			CommandDefinition cmdDefBefore = data.getSelectedCommands().get(0); 
			
			CommandInputDialog inputDialog = new CommandInputDialog(Display
					.getCurrent().getActiveShell(), cmdDefBefore.getContext());
			inputDialog.open();

			
			CommandKey newKey = inputDialog.getKey();

			if (newKey != null) {

				CommandWarehouse.getInstance().removeCommandKey(cmdDefBefore.getCommandKey());
				cmdDefBefore.getCommandKey().setAltKey(newKey.usesAlt());
				cmdDefBefore.getCommandKey().setCtrlKey(newKey.usesCtrl());
				cmdDefBefore.getCommandKey().setKey(newKey.usesKey());
				CommandWarehouse.getInstance().insertCommandDefinition(cmdDefBefore.getCommandKey(), cmdDefBefore);
				
			} else {
				System.err.println("The input dialog didn't returned any new valid key to update the commands system");
			}

		}

	}
}
