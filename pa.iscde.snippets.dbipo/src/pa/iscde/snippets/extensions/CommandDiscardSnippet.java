package pa.iscde.snippets.extensions;

import pa.iscde.commands.services.Command;
import pa.iscde.snippets.gui.CommandExtensionHelper;

public class CommandDiscardSnippet implements Command {

	@Override
	public void action() {
		CommandExtensionHelper.discardSnippetCommand();
	}

}
