package pa.iscde.commands.services;


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
	
	@Override
    public String toString(){
        return commandKey.getCommandName() + " ["+description+"] - Binding = " 
                    + commandKey.toString() + " - View = " + commandKey.getContext();
    }

	public void updateKey(CommandKey key) {
		this.commandKey = key;
	}
	
	
	
}