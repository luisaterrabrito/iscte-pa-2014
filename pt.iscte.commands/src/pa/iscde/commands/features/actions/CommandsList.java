package pa.iscde.commands.features.actions;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.internal.CommandViewTree;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.services.CommandDefinition;

public class CommandsList extends CommandViewTree {

	public CommandsList(Composite parentComposite, int... columsIndexToRemove) {
		super(parentComposite, columsIndexToRemove);
	}

	@Override
	protected void addDataToTreeTable() {

		Collection<CommandDefinition> contexts = CommandWarehouse.getInstance()
				.getAllCommandDefinitions();

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
