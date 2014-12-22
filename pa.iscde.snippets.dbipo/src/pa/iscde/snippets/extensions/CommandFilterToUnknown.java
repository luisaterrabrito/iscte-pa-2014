package pa.iscde.snippets.extensions;

import pa.iscde.commands.services.Command;
import pa.iscde.snippets.external.CommandExtensionHelper;
import pa.iscde.snippets.gui.SnippetsView;

public class CommandFilterToUnknown implements Command {

	@Override
	public void action() {
		CommandExtensionHelper.setFilterToLanguage("Unknown");
	}

}
