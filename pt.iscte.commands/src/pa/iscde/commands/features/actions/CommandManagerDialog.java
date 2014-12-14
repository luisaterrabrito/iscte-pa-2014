package pa.iscde.commands.features.actions;

import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
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
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.internal.CommandViewTree;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.models.ViewWarehouse;
import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;
import pa.iscde.commands.services.ViewDef;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pa.iscde.commands.utils.Labels;
import pt.iscte.pidesco.extensibility.PidescoServices;

class CommandManagerDialog extends Dialog {

	private CommandsList list;
	private Button rightToLeft;
	private Button leftToRight;

	private CommandViewTree commandsAndViews;
	private PidescoServices services;

	protected CommandManagerDialog(Shell parentShell) {
		super(parentShell);
		BundleContext context = CommandsController.getContext();
		ServiceReference<PidescoServices> ref = context
				.getServiceReference(PidescoServices.class);
		services = context.getService(ref);
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
		commandsAndViews = new CommandViewTree(commandsAndViewsComposite, false) {

			@Override
			protected void addDataToTreeTable() {
				super.addDataToTreeTable();
				Set<ViewDef> views = ViewWarehouse.getInstance()
						.getViewsWarehouse();
				for (ViewDef viewDef : views) {
					
					String viewIdentifier = viewDef.getUniqueIdentifier();
					
					if (!containView(commandTree.getItems(), viewIdentifier)) {
						TreeItem item = new TreeItem(commandTree, SWT.NONE);
						item.setBackground(new Color(null, 240, 240, 240));
						item.setText(viewIdentifier);
						item.setData(viewIdentifier);
					}

				}
			}

			private boolean containView(TreeItem[] items, String string) {
				String data = "";
				for (int i = 0; i < items.length; i++) {
					data = (String) items[i].getData();
					if (data.equals(string)) {
						return true;
					}
				}
				return false;
			}
		};

		Composite btns = new Composite(area, SWT.BORDER);
		btns.setLayout(new FillLayout(SWT.VERTICAL));
		rightToLeft = new Button(btns, SWT.NONE);
		rightToLeft.setText("<");
		rightToLeft.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		rightToLeft.addListener(SWT.Selection, new RightToLeftClick(this));
		rightToLeft.setToolTipText(Labels.RIGHTTOLEFTTIP);
		leftToRight = new Button(btns, SWT.NONE);
		leftToRight.setText(">");
		leftToRight.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		leftToRight.addListener(SWT.Selection, new LeftToRightClick(this));
		leftToRight.setToolTipText(Labels.LEFTTORIGHTTIP);

		Composite commandsList = new Composite(area, SWT.NONE);
		commandsList.setLayoutData(gridData);

		list = new CommandsList(commandsList, 1, 2);
	}

	protected CommandsList getList() {
		return list;
	}

	protected CommandViewTree getCommandsAndViews() {
		return commandsAndViews;
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
		services.runTool(
				ExtensionPointsIDS.REFRESH_COMMANDS_VEIW_ID.getID(), false);
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(900, 500);
	}

	private final class LeftToRightClick implements Listener {

		private CommandViewTree commandsAndViews;

		public LeftToRightClick(CommandManagerDialog newCommandDialog) {
			this.commandsAndViews = newCommandDialog.getCommandsAndViews();

		}

		@Override
		public void handleEvent(Event event) {
			TreeItem[] items = commandsAndViews.getCommandTree().getItems();
			for (int i = 0; i < items.length; i++) {
				TreeItem[] subItems = items[i].getItems();
				for (int j = 0; j < subItems.length; j++) {
					processSubItems(subItems[j]);
				}
			}
			commandsAndViews.refresh();
		}

		private void processSubItems(TreeItem subItem) {
			if (subItem.getChecked()) {
				CommandDefinition def = (CommandDefinition) subItem.getData();
				CommandWarehouse.getInstance().removeCommandKey(
						def.getCommandKey());

				subItem.dispose();
			}

		}
	}

	private final class RightToLeftClick implements Listener {

		private CommandManagerDialog newCommandDialog;

		public RightToLeftClick(CommandManagerDialog newCommandDialog) {
			this.newCommandDialog = newCommandDialog;
		}

		@Override
		public void handleEvent(Event event) {

			TreeItem[] items = newCommandDialog.getCommandsAndViews()
					.getCommandTree().getItems();

			int index = getSelectedIndex(items);
			if (index != -1
					&& newCommandDialog.getList().getNumSelectedItems() == 1) {

				CommandInputDialog dialog = new CommandInputDialog(Display
						.getCurrent().getActiveShell(), items[index].getData()
						.toString());
				dialog.open();

				addCommandsToView(dialog.getKey(), items[index].getData()
						.toString());

			}
		}

		private int getSelectedIndex(TreeItem[] items) {
			int oneViewSelected = 0;
			int index = -1;
			for (int i = 0; i < items.length; i++) {
				if (items[i].getChecked()) {
					oneViewSelected++;
					index = i;
				}
			}

			if (oneViewSelected > 1)
				index = -1;
			return index;
		}

		private void addCommandsToView(CommandKey commandKey, String view) {
			if (commandKey != null) {
				TreeItem[] items = newCommandDialog.getList().getCommandTree()
						.getItems();
				for (int i = 0; i < items.length; i++) {
					if (items[i].getChecked()) {
						CommandDefinition def = (CommandDefinition) items[i]
								.getData();

						commandKey
								.setName(def.getCommandKey().getCommandName());
						CommandWarehouse.getInstance().insertCommandDefinition(
								commandKey,
								new CommandDefinition(commandKey, view, def
										.getCommand(), def.getDescription()));
					}
				}
				newCommandDialog.getCommandsAndViews().refresh();
			}

		}
	}

}
