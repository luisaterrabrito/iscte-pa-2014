package pa.iscde.commands.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;

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
		return commandsWarehouse.get(key);
	}

	public CommandDefinition insertCommandDefinition(CommandKey key,
			CommandDefinition definition) {
		
		commandsByViewWarehouse.put(key.getContext(), definition);
		return commandsWarehouse.put(key, definition);
	}
	
	public List<CommandDefinition> getFilteredCommandsDefinitions(String text){
		List<CommandDefinition> lista = new LinkedList<CommandDefinition>();
		for(CommandKey ck : commandsWarehouse.keySet()){
			CommandDefinition def = commandsWarehouse.get(ck);
			if(def.toString().contains(text))
				lista.add(def);
		}
		
		return lista;
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

	//The collection returned is just a copy of all the commands defined in the warehouse
	//Changes made to the collection returned will not afect the warehouse
	public Collection<CommandDefinition> getAllCommandDefinitions(){
		return Collections.unmodifiableCollection(commandsWarehouse.values());
	}
	
	
	// Método auxiliar de debug
	public void printall(String string) {
		System.out.println("----"+string+" ("+commandsWarehouse.size()+") -----");
		for (CommandKey ke : commandsWarehouse.keySet()) {
			System.out.println("chave a null? " + ke == null);
			System.out.println("oi: " + ke + " - " + commandsWarehouse.get(ke));
		}
		System.out.println("----fim-----");
	}

	// Called when the Plug-in is destroyed (this needs to be done, in case to
	// of re-launch the plug-in in run-time)
	public static void eraseModel() {
		instance = null;
	}

}
