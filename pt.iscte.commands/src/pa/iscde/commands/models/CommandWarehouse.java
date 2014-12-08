package pa.iscde.commands.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.LinkedListMultimap;

public final class CommandWarehouse {

	private Map<CommandKey, CommandDefinition> commandsWarehouse;
	private LinkedListMultimap<String, CommandDefinition> commandsByViewWarehouse;
	private static CommandWarehouse instance;

	public static CommandWarehouse getInstance() {
		if (instance == null)
			instance = new CommandWarehouse();

		return instance;
	}

	private CommandWarehouse() {
		commandsWarehouse = new HashMap<CommandKey, CommandDefinition>();
		commandsByViewWarehouse = LinkedListMultimap.create();
	}

	public boolean containsKey(CommandKey key) {
		return commandsWarehouse.containsKey(key);
	}

	public CommandDefinition getCommandDefinition(CommandKey key) {
		System.out.println("tou à procura desta: " + key);

		System.out.println("antes: ");
		printall();
		CommandDefinition key2 = commandsWarehouse.get(key);
		System.out.println("encontrei la isto: " + key2.getCommandKey());
		System.out.println("depois");
		printall();

		return key2;
	}

	public CommandDefinition insertCommandDefinition(CommandKey key,
			CommandDefinition definition) {

		commandsByViewWarehouse.put(key.getContext(), definition);
		return commandsWarehouse.put(key, definition);
	}

	public Set<String> getCommandsContext() {
		return commandsByViewWarehouse.keySet();
	}

	public List<CommandDefinition> getCommandByContext(String context) {
		return commandsByViewWarehouse.get(context);
	}

	public boolean removeCommandKey(CommandKey key) {

		CommandDefinition commandDefinition = commandsWarehouse.remove(key);
		boolean result = commandsByViewWarehouse.remove(key.getContext(),
				commandDefinition);

		return commandDefinition != null && result;
	}

	// Método auxiliar de debug
	public void printall() {
		for (CommandKey ke : commandsWarehouse.keySet()) {
			System.out.println("oi: " + ke);
		}
	}

	// Called when the Plug-in is destroyed (this needs to be done, in case to
	// of re-launch the plug-in in run-time)
	public static void eraseModel() {
		instance = null;
	}

}
