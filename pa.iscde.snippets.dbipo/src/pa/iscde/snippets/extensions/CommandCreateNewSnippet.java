package pa.iscde.snippets.extensions;

import pa.iscde.commands.internal.services.Command;
import pa.iscde.snippets.gui.SnippetsView;
import activator.SnippetsActivator;

public class CommandCreateNewSnippet implements Command{

	@Override
	public void action() {
		// TODO Auto-generated method stub
			SnippetsView.getInstance().createNewSnippetCommand(SnippetsActivator.getInstance().getSelectedText());
	}

}