package pa.iscde.commands.services;

/**
 * 
 * This class represents the model of data of a single CommandDefinition, you can define the binding,
 * associate a view, the instance of a command implementation (execution code). and a simple description
 * to be showed on our CommandView
 * 
 * @author Tiago Martins
 * 
 **/


final public class CommandDefinition {
	
	private CommandKey commandKey;
	private String view;
	private Command command;
	private String description;
	
	/**
	 * 
	 * Constructor responsible to instantiate a CommandDefinition object
	 * 
	 * @param commandKey - The Command Key associated, represents the binding
	 * @param view - The view that represents the context where the command works
	 * @param command - The instance of a command implementation (the execution code you define)
	 * @param description - A simple line of text containing the description of what your command does
	 * 
	 */
	
	public CommandDefinition(CommandKey commandKey, String view, Command command, String description){
		this.commandKey = commandKey;
		this.view = view;
		this.command = command;
		this.description = description;
	}
	
	/**
	 * Method responsible to return the instance of your Command Key 
	 * @return CommandKey - instance of the associated Command Key
	 */

	public CommandKey getCommandKey(){
		return commandKey;
	}
	
	/**
	 * Method responsible to return the Unique Id of the view associated to this CommandDefinition,
	 * the syntax of the string is like (Plugin.Id).(Your_View_Class_Name)
	 * 
	 * @return String representing the UniqueId of the View (Context)
	 */
	
	public String getContext(){
		return view;
	}
	
	/**
	 * 
	 * Method responsible to return the instance of your Command implementation, containing the 
	 * definition of your execution code
	 * 
	 * @return
	 */
	
	public Command getCommand(){
		return command;
	}
	
	/**
	 * 
	 * Method responsible to return the description you associated to this Command Definition 
	 * 
	 * @return
	 */

	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * Method that return the hashCode of the current instance. A CommandDefinition is unique 
	 * by the XOR operation-wise of the following contents (CommandKey hashcode, String of Unique Id View hashcode, 
	 * Command instance hashcode and String of description hashcode)
	 * 
	 * 
	 */

	@Override
	public int hashCode(){
		return ((short)commandKey.hashCode()) ^ ((short)view.hashCode()) ^ 
				((short)command.hashCode()) ^ ((short) description.hashCode());
	}
	
	/**
	 * Method responsible to check if two objects of CommandDefinition class are equals.
	 * Two CommandDefinition are equals if the following contents are simultaneously equals
	 * (CommandKey, String of Unique Id View, Command instance and String of description)
	 * 
	 */
	
	@Override
	public boolean equals(Object o){
		
		if((o instanceof CommandDefinition) == false)
			return false;
		
		CommandDefinition cmdDef = (CommandDefinition) o;
		
		if(this == o)
			return true;
		
		if(commandKey.equals(cmdDef.commandKey) && command.equals(cmdDef.command) &&
				description.equals(cmdDef.description) && view.equals(cmdDef.view))
			return true;
		
		return false;
	}
	
	/**
	 * Method responsible to return the String Identifier of this object
	 * The syntax is as follows:
	 * CommandKeyName + [description] - Binding = CommandKey.toString() - View = StringUniqueIdView
	 * 
	 */
	
	@Override
    public String toString(){
        return commandKey.getCommandName() + " ["+description+"] - Binding = " 
                    + commandKey.toString() + " - View = " + commandKey.getContext();
    }
	
	/**
	 * 
	 * Method responsible to update the binding associated with this CommandDefinition object
	 * 
	 * @param key - The new CommandKey to update the old one
	 */

	public void updateKey(CommandKey key) {
		this.commandKey = key;
	}
	
	
	
}