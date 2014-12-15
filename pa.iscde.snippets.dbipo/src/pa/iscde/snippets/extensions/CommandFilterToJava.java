package pa.iscde.snippets.extensions;

import pa.iscde.commands.internal.services.Command;
import pa.iscde.snippets.gui.SnippetsExplorer;
import pa.iscde.snippets.gui.SnippetsView;

public class CommandFilterToJava implements Command {

	@Override
	public void action() {
			SnippetsView.getInstance().setFilterToJava();
	}
}
