package pa.iscde.commands.models;

import pa.iscde.commands.internal.services.Command;

public class CommandDefinition {
	
	private CommandKey commandKey;
	private String view;
	private Command command;
	private String description;
	
	public CommandDefinition(CommandKey commandKey, String view, Command command, String description){
		this.commandKey = commandKey;
		this.view = view;
		this.command = command;
		this.description = description;
	}

	public CommandKey getCommandKey(){
		return commandKey;
	}
	
	public String getContext(){
		return view;
	}
	
	public Command getCommand(){
		return command;
	}

	public String getDescription() {
		return description;
	}
	

	@Override
	public int hashCode(){
		return ((short)commandKey.hashCode()) ^ ((short)view.hashCode()) ^ 
				((short)command.hashCode()) ^ ((short) description.hashCode());
	}
	
	@Override
	public boolean equals(Object o){
		CommandDefinition cmdDef = (CommandDefinition) o;
		
		if(o == null)
			return false;
		
		if(this == o)
			return true;
		
		if(commandKey.equals(cmdDef.commandKey) && command.equals(cmdDef.command) &&
				description.equals(cmdDef.description) && view.equals(cmdDef.view))
			return true;
		
		return false;
	}
	
	
	
}