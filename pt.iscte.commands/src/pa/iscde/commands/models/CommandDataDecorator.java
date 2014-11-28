package pa.iscde.commands.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pa.iscde.commands.models.ShortKey;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This class uses the decorator pattern to provides the methods for handling
 * data in the extension point pa.iscde.commands.action
 * 
 * @author Fábio Martins
 * 
 * */
final public class CommandDataDecorator {

	private Tree tree;

	public CommandDataDecorator(Tree tree) {
		this.tree = tree;
	}

	/**
	 * This method returns all visible and selected commands from the list
	 * 
	 * @return {@link List} A unmodifiable list list with the commands
	 * */
	public List<ShortKey> getSelectedCommands() {
		LinkedList<ShortKey> kyes = new LinkedList<ShortKey>();

		TreeItem item = null;
		for (int i = 0; i < tree.getItems().length; i++) {
			item = tree.getItems()[i];
			validItem(kyes, item);
		}

		return Collections.unmodifiableList(kyes);
	}

	private void validItem(LinkedList<ShortKey> kyes, TreeItem item) {
		for (int j = 0; j < item.getItems().length; j++) {
			if (item.getItems()[j].getChecked()) {
				kyes.add((ShortKey) item.getItems()[j].getData());
			}
		}
	}

	/**
	 * Remove the command
	 * 
	 * @param key
	 *            The shortkey to remove
	 * */
	public void deleteCommand(ShortKey key) {
		// TODO apagar a shot key da lista onde a informação sobre todas as shorts keys esta guadada
		// TODO actualiza na tree
	}

	/**
	 * Insert a new command
	 * 
	 * @param key
	 *            the shortkey ro insert
	 * */
	public void insertCommand(ShortKey key) {
		// TODO inserir uma nova short key onde a informação sobre todas as shorts keys esta guadada
		// TODO actualiza na tree
	}

	/**
	 * This method returns all the commands that are visible in the list
	 * 
	 * @return {@link List} A unmodifiable list list with the commands
	 * */
	public List<ShortKey> getAllCommands() {
		LinkedList<ShortKey> kyes = new LinkedList<ShortKey>();

		TreeItem item = null;
		for (int i = 0; i < tree.getItems().length; i++) {
			item = tree.getItems()[i];
			for (int j = 0; j < item.getItems().length; j++) {
				kyes.add((ShortKey) item.getItems()[j].getData());
			}
		}

		return Collections.unmodifiableList(kyes);
	}

}
