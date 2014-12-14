package pa.iscde.commands.services;

import java.util.List;

public interface CommandServices {
	
	public List<CommandDefinition> getAllCommands();
	public boolean addCommand(CommandDefinition c);
	public boolean removeCommand(CommandDefinition c);
	public boolean requestBindingEdition(CommandDefinition c);
	public List<CommandDefinition> getFilteredCommands(String text);
	public ViewDef getViewDefFromUniqueIdentifier(String o);
	
}
