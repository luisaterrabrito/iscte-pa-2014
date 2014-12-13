package pa.iscde.snippets.extensions;

import pa.iscde.commands.internal.services.Command;
import pa.iscde.snippets.gui.SnippetsExplorer;

public class CommandFilterToJava implements Command {

	@Override
	public void action() {
		SnippetsExplorer.getInstance().setFilterToJava();
	}
}
