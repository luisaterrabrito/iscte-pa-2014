package pa.iscde.commands.features.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.external.services.CommandViewTree;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.utils.Labels;

public class NewCommandDialog extends Dialog {

	private CommandsList list;
	private Button rightToLeft;
	private Button leftToRight;

	private CommandViewTree commandsAndViews;

	protected NewCommandDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		addViewsAndCommands(container);

		return container;
	}

	private void addViewsAndCommands(Composite container) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		new Label(container, SWT.NONE).setText(Labels.VIEWSANDCOMMANDS_LBL);

		Composite area = new Composite(container, SWT.NONE);
		area.setLayoutData(gridData);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		area.setLayout(gridLayout);

		Composite commandsAndViewsComposite = new Composite(area, SWT.NONE);
		commandsAndViewsComposite.setLayoutData(gridData);
		commandsAndViews = new CommandViewTree(commandsAndViewsComposite, false);

		Composite btns = new Composite(area, SWT.BORDER);
		btns.setLayout(new FillLayout(SWT.VERTICAL));
		rightToLeft = new Button(btns, SWT.NONE);
		rightToLeft.setText("<");
		rightToLeft.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		rightToLeft.addListener(SWT.Selection, new RightToLeftClick());
		leftToRight = new Button(btns, SWT.NONE);
		leftToRight.setText(">");
		leftToRight.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		leftToRight.addListener(SWT.Selection, new LeftToRightClick());

		Composite commandsList = new Composite(area, SWT.NONE);
		commandsList.setLayoutData(gridData);

		list = new CommandsList(commandsList, 1, 2);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.VIEWSANDCOMMANDS_LBL);
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		// refresh commands view
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(900, 500);
	}

	private final class LeftToRightClick implements Listener {
		@Override
		public void handleEvent(Event event) {
			TreeItem[] items = commandsAndViews.getCommandTree().getItems();
			for (int i = 0; i < items.length; i++) {
				TreeItem[] subItems = items[i].getItems();
				for (int j = 0; j < subItems.length; j++) {
					processSubItems(subItems[j]);
				}
			}
		}

		private void processSubItems(TreeItem subItem) {
			if (subItem.getChecked()) {
				// TODO remover da view o commando
			}

		}
	}

	private final class RightToLeftClick implements Listener {
		@Override
		public void handleEvent(Event event) {

			TreeItem[] items = commandsAndViews.getCommandTree().getItems();
			int oneViewSelected = 0;
			int index = -1;
			for (int i = 0; i < items.length; i++) {
				if (items[i].getChecked()) {
					oneViewSelected++;
					index = i;
				}
			}

			if (oneViewSelected == 1 && list.getNumSelectedItems() > 0) {
				if (items[index].getData() instanceof String) {

					CommandInputDialog dialog = new CommandInputDialog(Display
							.getCurrent().getActiveShell(), items[index]
							.getData().toString());
					dialog.open();

					addCommandsToView(dialog.getKey(), items[index].getData()
							.toString()); 
				}
			}

		}

		private void addCommandsToView(CommandKey commandKey, String view) {
			if (commandKey != null) {
				// TODO adicionar comando a view e á tree

			}

		}
	}

}
