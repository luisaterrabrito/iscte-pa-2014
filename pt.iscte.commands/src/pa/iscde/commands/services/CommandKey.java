package pa.iscde.commands.services;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 * This class represents the model of data of a single CommandKey, this class represents a specific binding
 * of a single CommandDefinition
 * associate a view, the instance of a command implementation (execution code). and a simple description
 * to be showed on our CommandView
 * 
 * @author Tiago Martins
 * 
 **/

final public class CommandKey {

	private String name;
	private String view;
	private boolean ctrl_key;
	private boolean alt_key;
	private char key;

	private List<TreeItem> dataObservers;

	
	/**
	 * 
	 * Constructor responsible to instantiate a CommandKey object
	 * 
	 * @param view - The view that represents the context where the command works
	 * @param ctrl_key - A boolean indicating if this binding uses the CTRL key
	 * @param alt_key - A boolean indicating if this binding uses the ALT key
	 * @param key - A char indicating which key (A-Za-z) the binding uses
	 * 
	 */
	
	public CommandKey(String view, boolean ctrl_key, boolean alt_key, char key) {
		this.ctrl_key = ctrl_key;
		this.alt_key = alt_key;
		this.key = Character.toLowerCase(key);
		this.view = view;
		dataObservers = new LinkedList<TreeItem>();
	}
	
	/**
	 * 
	 * Constructor responsible to instantiate a CommandKey object
	 * 
	 * @param name - The name of the command
	 * @param view - The view that represents the context where the command works
	 * @param ctrl_key - A boolean indicating if this binding uses the CTRL key
	 * @param alt_key - A boolean indicating if this binding uses the ALT key
	 * @param key - A char indicating which key (A-Za-z) the binding uses
	 * 
	 */

	public CommandKey(String name, String view, boolean ctrl_key,
			boolean alt_key, char key) {
		this(view, ctrl_key, alt_key, key);
		this.name = name;
		dataObservers = new LinkedList<TreeItem>();
	}

	/**
	 * Method responsible to get the name of the CommandKey object 
	 * @return name - The name of the command
	 */
	
	public String getCommandName() {
		return name;
	}
	
	/**
	 * Method responsible to set the name of the CommandKey object 
	 * @param name - The name for the command
	 */
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method responsible to get a boolean indicating if binding uses CTRL key 
	 * @return ctrl_key - True if binding uses CTRL, False otherwise
	 */

	public boolean usesCtrl() {
		return ctrl_key;
	}

	/**
	 * Method responsible to get a boolean indicating if binding uses ALT key 
	 * @return alt_key - True if binding uses ALT, False otherwise
	 */
	
	public boolean usesAlt() {
		return alt_key;
	}
	
	/**
	 * Method responsible to get the char associated with this binding 
	 * @return key - The char used by the binding
	 */

	public char usesKey() {
		return key;
	}

	/**
	 * Method responsible to get the View associated with this binding (the context) 
	 * @return view - String with UniqueId of View associated
	 */
	
	
	public String getContext() {
		return view;
	}
	
	/**
	 * Method responsible to set the state of use the ALT key 
	 * @param alt_key - True makes the binding use the ALT key, False otherwise
	 */
	

	public void setAltKey(boolean alt_key) {
		this.alt_key = alt_key;
		notifyObserver();
	}
	
	/**
	 * Method responsible to set the state of use the CTRL 
	 * @param alt_key - True makes the binding use the CTRL key, False otherwise
	 */

	public void setCtrlKey(boolean ctrl_key) {
		this.ctrl_key = ctrl_key;
		notifyObserver();
	}

	/**
	 * Method responsible to set the key which this binding uses 
	 * @param key - A char indicating which key this binding uses
	 */
	
	public void setKey(char key) {
		this.key = key;
		notifyObserver();
	}
	
	/**
	 * Method responsible to add a specific TreeItem as observer of our models,
	 * If some data of a specific instance of this class changes, we notify all the TreeItem
	 * instances so they might update their data also if changed. 
	 * @param observer
	 */

	public void addObserver(TreeItem observer) {
		dataObservers.add(observer);
		clearDisposeObservers();
	}
	
	/**
	 * 
	 * Method that return the hashCode of the current instance. A CommandKey is unique 
	 * by the XOR operation-wise of the following contents (String of Unique Id View hashcode, 
	 * boolean value of ctrl_key state hashcode, boolean value of alt_key state hashcode,
	 * char value of key hashcode)
	 * 
	 * 
	 */

	@Override
	public int hashCode(){
		return ((short) view.hashCode()) ^ ((short) Boolean.valueOf(ctrl_key).hashCode()) ^ 
				((short) Boolean.valueOf(alt_key).hashCode()) ^ ((short) Character.valueOf(key).hashCode());
	}
	
	/**
	 * Method responsible to check if two objects of CommandKey class are equals.
	 * Two CommandKey are equals if the following contents are simultaneously equals
	 * (String of Unique Id View, State of CTRL_KEY, State of ALT_KEY, Character of Key)
	 * 
	 */
	
	@Override
	public boolean equals(Object o){
		
		if((o instanceof CommandKey) == false)
			return false;
		
		if(this == o )
			return true;
	
		CommandKey command = (CommandKey) o;
		if(view.equals(command.view) && ctrl_key == command.ctrl_key && 
				alt_key == command.alt_key && key == command.key)
			return true;
		
		return false;
		
	}
	
	/**
	 * Method responsible to check if two objects of this class are equals.
	 * Two CommandKey have their key equals if the following contents are simultaneously equals
	 * (State of CTRL key, State of ALT key, Character of Key)
	 */

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

	
	/**
	 * Method responsible to return the String Identifier of this object
	 * The syntax is as follows:
	 * If CTRL state is true and ALT state is false
	 * CTRL + 'KEY'
	 * If CTRL state is false and ALT state is true
	 * ALT + 'KEY'
	 * If CTRL state is true and ALT state is true
	 * CTRL + ALT + 'KEY'
	 */
	
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

}