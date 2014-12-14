package pa.iscde.commands.services;

import pa.iscde.commands.models.CommandDataAdaptor;


/**
 * This class allow to be added buttons to command view in order to manipulate
 * your content
 * 
 * @author Fábio Martins
 * */
public interface Action {

	/**
	 * The action to be performed when the button is clicked
	 * 
	 * @param data
	 *            Contains the methods to manipulate the elements in the command
	 *            view
	 * */
	public void action(CommandDataAdaptor data);
}
