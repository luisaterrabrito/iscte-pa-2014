package pa.iscde.commands.services;

/**
 * 
 * This interface allows you to define the lines of code you want to 
 * execute when your command binding is pressed
 * 
 * @author Tiago Martins
 * 
 **/

public interface Command {
	

	/**
	 * 
	 * The action to be performed when the binding is pressed
	 * 
	 **/
	
	void action();

}
