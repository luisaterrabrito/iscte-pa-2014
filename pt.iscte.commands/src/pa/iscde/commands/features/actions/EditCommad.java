package pa.iscde.commands.features.actions;

import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.internal.services.Action;
import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;

public class EditCommad implements Action {

	@Override
	public void action(CommandDataAdaptor data) {
		System.out.println("p1");
		CommandWarehouse.printall();
		System.out.println("p2");
		if (data.getSelectedCommands().size() == 1) {
			CommandInputDialog inputDialog = new CommandInputDialog(Display.getCurrent().getActiveShell(), data.getSelectedCommands().get(0));

			inputDialog.open();

			CommandKey newKey = inputDialog.getKey();
			
			if (newKey != null) {
				
				CommandKey key = data.getSelectedCommands().get(0).getCommandKey();

				key.setAltKey(newKey.usesAlt());
				key.setCtrlKey(newKey.usesCtrl());
				key.setKey(newKey.usesKey());

			}else{
				System.out.println("key null");
			}

		}
		System.out.println("p3");
		CommandWarehouse.printall();
		System.out.println("p4");

	}
}
