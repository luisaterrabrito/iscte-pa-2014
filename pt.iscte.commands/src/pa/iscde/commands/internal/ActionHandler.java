package pa.iscde.commands.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;

import pa.iscde.commands.controllers.ExtensionHandler.Handler;
import pa.iscde.commands.models.CommandDataAdaptor;
import pa.iscde.commands.services.Action;

final class ActionHandler implements Handler {

	private Composite actionsArea;
	private Tree tree;

	public ActionHandler(Composite actionsArea, CommandViewTree commandTree) {
		this.actionsArea = actionsArea;
		this.tree = commandTree.getCommandTree();
	}

	@Override
	public void processExtension(IConfigurationElement e) throws CoreException {
		String label = (String) e.getAttribute("label");
		final Action action = (Action) e.createExecutableExtension("class");

		Button btn = new Button(actionsArea, SWT.PUSH);
		btn.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		btn.setText(label);
		btn.addListener(SWT.Selection, new Listener() {

			private CommandDataAdaptor data = new CommandDataAdaptor(tree);

			@Override
			public void handleEvent(Event event) {
				action.action(data);
			}
		});
	}
}
