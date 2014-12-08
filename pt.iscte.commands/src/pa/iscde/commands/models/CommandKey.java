package pa.iscde.commands.models;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.TreeItem;

final public class CommandKey {

	private String name;
	private String view;
	private boolean ctrl_key;
	private boolean alt_key;
	private char key;

	private List<TreeItem> dataObservers;

	public CommandKey(String view, boolean ctrl_key, boolean alt_key, char key) {
		this.ctrl_key = ctrl_key;
		this.alt_key = alt_key;
		this.key = Character.toLowerCase(key);
		this.view = view;
		dataObservers = new LinkedList<TreeItem>();
	}

	public CommandKey(String name, String view, boolean ctrl_key,
			boolean alt_key, char key) {
		this(view, ctrl_key, alt_key, key);
		this.name = name;
		dataObservers = new LinkedList<TreeItem>();
	}

	public String getCommandName() {
		return name;
	}

	public boolean usesCtrl() {
		return ctrl_key;
	}

	public boolean usesAlt() {
		return alt_key;
	}

	public char usesKey() {
		return key;
	}

	public String getContext() {
		return view;
	}

	public void setAltKey(boolean alt_key) {
		this.alt_key = alt_key;
		notifyObserver();
	}

	public void setCtrlKey(boolean ctrl_key) {
		this.ctrl_key = ctrl_key;
		notifyObserver();
	}

	public void setKey(char key) {
		this.key = key;
		notifyObserver();
	}

	public void addObserver(TreeItem observer) {
		dataObservers.add(observer);
		clearDisposeObservers();
	}

	private void clearDisposeObservers() {
		List<TreeItem> toRemove = new LinkedList<TreeItem>();
		for (TreeItem commandKeyObserver : dataObservers) {
			if (commandKeyObserver.isDisposed()) {
				toRemove.add(commandKeyObserver);
			}
		}

		for (TreeItem treeItem : toRemove) {
			dataObservers.remove(treeItem);
		}
	}

	@Override
	public int hashCode(){
		return ((short) view.hashCode()) ^ ((short) Boolean.valueOf(ctrl_key).hashCode()) ^ 
				((short) Boolean.valueOf(alt_key).hashCode()) ^ ((short) Character.valueOf(key).hashCode());
	}
	
	@Override
	public boolean equals(Object o){
		
		if(o == null)
			return false;
		
		if(this == o )
			return true;
	
		CommandKey command = (CommandKey) o;
		if(view.equals(command.view) && ctrl_key == command.ctrl_key && 
				alt_key == command.alt_key && key == command.key)
			return true;
		
		return false;
		
	}

	public boolean keyEquals(Object o) {
		if (o == null)
			return false;

		if (this == o)
			return true;

		CommandKey command = (CommandKey) o;
		if (ctrl_key == command.ctrl_key && alt_key == command.alt_key
				&& key == command.key)
			return true;

		return false;
	}

	@Override
	public String toString() {
		String result = "";
		if (ctrl_key)
			result = "CTRL + ";

		if (alt_key && !ctrl_key)
			result = "ALT + ";
		else if (alt_key && ctrl_key)
			result = result + "ALT + ";

		return result + Character.toUpperCase(key);
	}

	private void notifyObserver() {
		CommandDefinition command = null;
		for (TreeItem commandKeyObserver : dataObservers) {

			command = (CommandDefinition) commandKeyObserver.getData();
			commandKeyObserver.setText(new String[] {
					command.getCommandKey().getCommandName(),
					command.getDescription(),
					command.getCommandKey().toString() });

		}

		clearDisposeObservers();
	}

}