package pa.iscde.commands.features.actions;

import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.internal.services.Action;
import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.models.CommandKey;

public class EditCommad implements Action {

	@Override
	public void action(CommandDataAdaptor data) {

		if (data.getSelectedCommands().size() == 1) {
			CommandInputDialog inputDialog = new CommandInputDialog(Display
					.getCurrent().getActiveShell(), data.getSelectedCommands().get(0));

			inputDialog.open();

			if (inputDialog.getKey() != null) {
				CommandKey key = data.getSelectedCommands().get(0).getCommandKey();

				key.setAltKey(inputDialog.getKey().usesAlt());
				key.setCtrlKey(inputDialog.getKey().usesCtrl());
				key.setKey(inputDialog.getKey().usesKey());

			}

		}

	}
}
