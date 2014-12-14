package pa.iscde.commands.internal;

import pt.iscte.pidesco.extensibility.PidescoTool;

public class RefreshCommandsView implements PidescoTool {

	@Override
	public void run(boolean activate) {

		CommandView view = CommandView.instance;

		if (view != null) {
			view.getCommandTree().refresh();
		}
	}

}
