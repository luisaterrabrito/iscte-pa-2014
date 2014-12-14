package pa.iscde.commands.controllers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import pa.iscde.commands.controllers.ExtensionHandler.Handler;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.services.Command;
import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;

public class CommandExtensionHandler implements Handler {

	enum TypesOfCommand {
		command_view, command_editor
	}

	private static final String EDITOR_VIEW_ID = "pt.iscte.pidesco.javaeditor";

	@Override
	public void processExtension(IConfigurationElement e) throws CoreException {

		String name = e.getAttribute("name");
		String ctrl_key = e.getAttribute("ctrl_key");
		String alt_key = e.getAttribute("alt_key");
		String key = e.getAttribute("key");
		String view = computeContext(e);
		String description = e.getAttribute("description");
		Command command = (Command) e
				.createExecutableExtension("command_implementation");

		CommandKey cmdKey = new CommandKey(name, view,
				Boolean.valueOf(ctrl_key), Boolean.valueOf(alt_key),
				key.charAt(0));
		CommandDefinition replacedValue = CommandWarehouse.getInstance()
				.insertCommandDefinition(
						cmdKey,
						new CommandDefinition(cmdKey, view, command,
								description));

		if (replacedValue != null)
			System.err
					.println("WARNING: While loading the commands extensions, the command with the name '"
							+ replacedValue.getCommandKey().getCommandName()
							+ "' got replaced by the new command '"
							+ name
							+ "',  "
							+ "if you don't want it to happen again please give unique keys combinations to your commands");

	}

	private String computeContext(IConfigurationElement e) {

		if (e.getName().equals(TypesOfCommand.command_view.toString()))
			return e.getAttribute("context_view");
		else
			return EDITOR_VIEW_ID;
	}

}
