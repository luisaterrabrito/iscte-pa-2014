package pa.iscde.snippets.extensions;

import pa.iscde.commands.services.Command;
import pa.iscde.snippets.gui.CommandExtensionHelper;

public class CommandSaveCurrentSnippet implements Command{

	@Override
	public void action() {
		CommandExtensionHelper.savedSnippetCommand();
	}

}
