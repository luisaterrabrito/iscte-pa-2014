package pa.iscde.commands.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;

/**
 * This class uses the decorator pattern to provides the methods for handling
 * data in the extension point pa.iscde.commands.action
 * 
 * @author F�bio Martins
 * 
 * */
final public class CommandDataAdaptor {

	private Tree tree;

	public CommandDataAdaptor(Tree tree) {
		this.tree = tree;
	}

	/**
	 * This method returns all visible and selected commands from the list
	 * 
	 * @return {@link List} A unmodifiable list list with the commands
	 * */
	public List<CommandDefinition> getSelectedCommands() {
		LinkedList<CommandDefinition> kyes = new LinkedList<CommandDefinition>();

		TreeItem item = null;
		for (int i = 0; i < tree.getItems().length; i++) {
			item = tree.getItems()[i];
			validItem(kyes, item);
		}

		return kyes;
	}

	private void validItem(LinkedList<CommandDefinition> kyes, TreeItem item) {
		for (int j = 0; j < item.getItems().length; j++) {
			if (item.getItems()[j].getChecked()) {
				kyes.add((CommandDefinition) item.getItems()[j].getData());
			}
		}
	}

	/**
	 * Remove the command
	 * 
	 * @param key
	 *            - The CommandKey to remove
	 * */
	public void deleteCommandLine(CommandKey key) {

		Point keyInTree = findKeyInTree(CommandWarehouse.getInstance()
				.getCommandDefinition(key));
		tree.getItem(keyInTree.x).getItem(keyInTree.y).dispose();
		if (CommandWarehouse.getInstance().removeCommandKey(key))
			System.out.println("Command name '" + key.getCommandName()
					+ "' was removed successfully");

	}

	private Point findKeyInTree(CommandDefinition key) {

		int i = 0;
		for (TreeItem tree_ : tree.getItems()) {
			int j = 0;
			for (TreeItem subTree : tree_.getItems()) {

				if (subTree.getData().equals(key)) {
					return new Point(i, j);
				}
				j++;
			}
			i++;
		}
		return null;
	}

	private int findContextInTree(String key) {

		int i = 0;
		for (TreeItem treeContext : tree.getItems()) {

			if (treeContext.getData().toString().equals(key.toString()))
				return i;
			i++;
		}
		return -1;
	}

	/**
	 * Insert a new command
	 * 
	 * @param cmdDef
	 *            - the CommandDefinition to insert
	 * */
	public boolean insertCommandLine(CommandDefinition cmdDef) {

		int i = findContextInTree(cmdDef.getContext());
		if (i != -1) {
			
			boolean insertResult = insertCommand(cmdDef); 
			
			if (insertResult
					&& CommandWarehouse.getInstance().containsKey(
							cmdDef.getCommandKey())) {

				TreeItem subItem = new TreeItem(tree.getItem(i), SWT.NONE);
				subItem.setData(cmdDef);
				subItem.setText(new String[] {
						cmdDef.getCommandKey().getCommandName(),
						cmdDef.getDescription(),
						cmdDef.getCommandKey().toString() });
				subItem.getParentItem().setExpanded(true);

				System.out.println("Command name '"
						+ cmdDef.getCommandKey().getCommandName()
						+ "' was successfully added");
				return true;

			} else {
				System.out
						.println("Command name '"
								+ cmdDef.getCommandKey().getCommandName()
								+ "' wasn't added, there was a problem, "
								+ "maybe there is already a command with the same binding for the same context!");
			}

		}
		return false;

	}

	/**
	 * This method returns all the commands that are visible in the list
	 * 
	 * @return {@link List} A unmodifiable list list with the commands
	 * */
	public List<CommandDefinition> getAllCommands() {
		LinkedList<CommandDefinition> kyes = new LinkedList<CommandDefinition>();

		TreeItem item = null;
		for (int i = 0; i < tree.getItems().length; i++) {
			item = tree.getItems()[i];
			for (int j = 0; j < item.getItems().length; j++) {
				kyes.add((CommandDefinition) item.getItems()[j].getData());
			}
		}

		return Collections.unmodifiableList(kyes);
	}
	
	
	/**
	 * This method returns all the commands in the list filtered by a specific string  
	 * 
	 * @return {@link List} A unmodifiable list list with the commands
	 * */
	public List<CommandDefinition> getFilteredCommands(String text) {
		return CommandWarehouse.getInstance().getFilteredCommandsDefinitions(text);
	}
	
	
	
	

	
	/**
	 * This methods allows you to edit a existing Command Line in the tree
	 * Changing the CommandKey associated with the Definition before in the system.
	 * 
	 * @param cmdDefBefore
	 * @param newKey
	 * 
	 * @return True - if the edit was successfully, False otherwise 
	 */
	public boolean editCommand(CommandDefinition cmdDefBefore,
			CommandKey newKey) {

		boolean removeResult = removeCommand(cmdDefBefore);
		
		cmdDefBefore.getCommandKey().setAltKey(newKey.usesAlt());
		cmdDefBefore.getCommandKey().setCtrlKey(newKey.usesCtrl());
		cmdDefBefore.getCommandKey().setKey(newKey.usesKey());
		
		boolean insertResult = insertCommand(cmdDefBefore);
		
		return removeResult && insertResult;
	}

	public boolean insertCommand(CommandDefinition c) {
		CommandDefinition insertResult = CommandWarehouse.getInstance().insertCommandDefinition(c.getCommandKey(), c );
		return insertResult == null;
	}

	public boolean removeCommand(CommandDefinition c) {
		return CommandWarehouse.getInstance().removeCommandKey(c.getCommandKey());
	}

}
