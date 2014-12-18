package pa.iscde.commands.services;

import java.util.List;

	/**
 	* 
	* This interface allow you get the an instance of a CommandServices implementation
	* that is registered as a Service in our plugin.
	* Getting the Service Reference using this interface class returns a CommandServices object
	* where there are already implementations of these methods, they manipulate the data from our Warehouse,
	* which you don't have access to.
	* This Service helps us to protect our data, only giving you some points of access using this bridge methods.
	* 
	* @author Tiago Martins
	* 
	**/


public interface CommandServices {
	
	
	
	/**
	 * 
	 * Method responsible to return a list of all the CommandDefinitions defined in our warehouse
	 * @return - List of all CommandDefinition instances
	 * 
	 */
	
	public List<CommandDefinition> getAllCommands();
	
	
	/**
	 * Method responsible to add a new CommandDefinition to our warehouse
	 * 
	 * @param c - The CommandDefinition to add
	 * @return boolean - True if operation had success, False otherwise
	 * 
	 */
	
	public boolean addCommand(CommandDefinition c);
	
	
	/**
	 * Method responsible to remove a specific CommandDefinition from our warehouse
	 * @param c - The CommandDefinition to remove
	 * @return boolean - True if operation had sucess, False otherwise  
	 */
	
	public boolean removeCommand(CommandDefinition c);
	
	
	/**
	 * Method that launchs our Small GUI with the purpose of editing the CommandDefinition to a new one,
	 * affecting the data in our warehouse also, if there is an equal CommandDefinition
	 * 
	 * @param c - The CommandDefinition to edit
	 * @return boolean - True if operation had sucess, False otherwise  
	 */
	
	public boolean requestBindingEdition(CommandDefinition c);
	
	
	/**
	 * 
	 * Method responsible to return a list of all the CommandDefinitions defined in our warehouse, filtered by
	 * a String of text in their toString() name
	 * 
	 * @param text - The String to use as filter
	 * @return - List of all the filtered CommandDefinition instances
	 * 
	 */
	
	public List<CommandDefinition> getFilteredCommands(String text);
	
	
	/**
	 * Method responsible to return an instance of ViewDef class representing a specific
	 * View UniqueId. It must be a valid UniqueId, the ViewWarehouse must have a View with the same UniquId, otherwise
	 * the method returns null
	 * @param o
	 * @return
	 */
	
	public ViewDef getViewDefFromUniqueIdentifier(String o);
	
}
