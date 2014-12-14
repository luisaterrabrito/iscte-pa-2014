package pa.iscde.commands.models;

import java.util.List;

import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.features.actions.CommandInputDialog;
import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;
import pa.iscde.commands.services.CommandServices;
import pa.iscde.commands.services.ViewDef;

public class CommandServicesImpl implements CommandServices {
	
	private CommandDataAdaptor adaptor;
	
	public CommandServicesImpl(CommandDataAdaptor adaptor){
		this.adaptor = adaptor;
	}

	@Override
	public List<CommandDefinition> getAllCommands() {
		return adaptor.getAllCommands();
	}

	@Override
	public boolean addCommand(CommandDefinition c) {
		return adaptor.insertCommand(c);
	}

	@Override
	public boolean removeCommand(CommandDefinition c) {
		return adaptor.removeCommand(c);
	}
	
	@Override
	public boolean requestBindingEdition(CommandDefinition c){
		
		CommandInputDialog inputDialog = new CommandInputDialog(Display.getCurrent().getActiveShell(), c.getContext());
		inputDialog.open();

		CommandKey newKey = inputDialog.getKey();

		if (newKey != null) {
			boolean resultEdit = adaptor.editCommand(c, newKey);
			return resultEdit;
		} else {
			System.err.println("The input dialog didn't returned any new valid key to update the commands system");
			return false;
		}
		
		
	}
	
	@Override
	public List<CommandDefinition> getFilteredCommands(String text){
		return adaptor.getFilteredCommands(text);
	}
	
	@Override
	public ViewDef getViewDefFromUniqueIdentifier(String o){
		return ViewWarehouse.getInstance().getViewDefFromUniqueIdentifier(o);
	}
	

}
