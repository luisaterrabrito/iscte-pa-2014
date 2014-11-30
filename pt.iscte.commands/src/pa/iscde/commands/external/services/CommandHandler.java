package pa.iscde.commands.external.services;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import pa.iscde.commands.controllers.ExtensionHandler.Handler;
import pa.iscde.commands.internal.services.Command;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;

public class CommandHandler implements Handler {

	@Override
	public void processExtension(IConfigurationElement e)
			throws CoreException {
		
		String name = e.getAttribute("name");
		String ctrl_key = e.getAttribute("ctrl_key");
		String alt_key = e.getAttribute("alt_key");
		String key = e.getAttribute("key");
		String view = e.getAttribute("context_view");
		String description = e.getAttribute("description");
		Command command = (Command) e.createExecutableExtension("command_implementation");
		
		CommandKey cmdKey = new CommandKey(name, view, Boolean.valueOf(ctrl_key), Boolean.valueOf(alt_key), key.charAt(0));
		CommandDefinition replacedValue = CommandWarehouse.insertCommandDefinition(cmdKey, new CommandDefinition(cmdKey, view, command, description));
		
		if(replacedValue != null)
			System.err.println("WARNING: While loading the commands extensions, the command with the name '" + replacedValue.getCommandKey().getCommandName() + "' got replaced by the new command '"+ name + "',  if you don't want it to happen again please give unique keys combinations to your commands");
		

	}

}
