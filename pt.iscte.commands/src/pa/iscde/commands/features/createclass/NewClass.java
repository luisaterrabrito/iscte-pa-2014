package pa.iscde.commands.features.createclass;

import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.internal.services.Command;

final public class NewClass implements Command {

	private NewClassDialog dialog;

	@Override
	public void action() {
		dialog = new NewClassDialog(Display.getCurrent().getActiveShell());
		dialog.open();

	}

}
