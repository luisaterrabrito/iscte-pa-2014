package pa.iscde.snippets.extensions;

import pa.iscde.commands.services.Command;
import pa.iscde.snippets.external.CommandExtensionHelper;
import pa.iscde.snippets.gui.SnippetsView;
import activator.SnippetsActivator;

public class CommandCreateNewSnippet implements Command{

	@Override
	public void action() {
			CommandExtensionHelper.createNewSnippetCommand(SnippetsActivator.getInstance().getSelectedText());
	}

}
