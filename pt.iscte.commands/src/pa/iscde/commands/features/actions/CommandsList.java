package pa.iscde.commands.features.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.external.services.CommandViewTree;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandWarehouse;

public class CommandsList extends CommandViewTree {

	public CommandsList(Composite parentComposite, int... columsIndexToRemove) {
		super(parentComposite, columsIndexToRemove);
	}

	@Override
	protected void addDataToTreeTable() {

		List<CommandDefinition> contexts = new LinkedList<CommandDefinition>();

		// TODO Pode ficar mais efeciente se for retornada alista de todos os
		// comandos definidos
		Set<String> aux = CommandWarehouse.getInstance().getCommandsContext();
		for (String context : aux) {
			for (CommandDefinition it : CommandWarehouse.getInstance()
					.getCommandByContext(context)) {
				contexts.add(it);
			}
		}

		String match = textField.getText();

		for (CommandDefinition command : contexts) {
			TreeItem item = new TreeItem(commandTree, SWT.NONE);
			item.setData(command);
			item.setText(new String[] { command.getDescription() });

			if (match.length() != 0 && !item.getText().contains(match)) {
				item.dispose();
			}

		}

	}
}
