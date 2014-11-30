package pa.iscde.commands.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.LinkedListMultimap;


public class CommandWarehouse {
	
	private static Map<CommandKey, CommandDefinition> commandsWarehouse;
	private static LinkedListMultimap<String,CommandDefinition> commandsByViewWarehouse;
	
	static {
		if(commandsWarehouse == null)
			commandsWarehouse = new HashMap<CommandKey, CommandDefinition>();
		
		if(commandsByViewWarehouse == null)
			commandsByViewWarehouse = LinkedListMultimap.create();
		
	}
	
	public static boolean containsKey(CommandKey key){
		return commandsWarehouse.containsKey(key);
	}
	
	public static CommandDefinition getCommandDefinition(CommandKey key){
		return commandsWarehouse.get(key);
	}
	
	public static CommandDefinition insertCommandDefinition(CommandKey key, CommandDefinition definition){
		
		commandsByViewWarehouse.put(key.getContext(), definition);
		return commandsWarehouse.put(key, definition);
	}
	
	public static Set<String> getCommandsContext(){
		return commandsByViewWarehouse.keySet();
	}
	
	public static List<CommandDefinition> getCommandByContext(String context){
		return commandsByViewWarehouse.get(context);
	}
	
	public static boolean removeCommandKey(CommandKey key) {
	
		CommandDefinition commandDefinition = commandsWarehouse.remove(key);
		boolean result = commandsByViewWarehouse.remove(key.getContext(), commandDefinition);
		
		return commandDefinition != null && result;
	}
	
	//Método auxiliar de debug
	public static void printall(){
		for(CommandKey ke : commandsWarehouse.keySet()){
			System.out.println(ke);
		}
	}

}
