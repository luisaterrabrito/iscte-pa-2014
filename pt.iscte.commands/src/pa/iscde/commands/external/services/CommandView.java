package pa.iscde.commands.external.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.controllers.ExtensionHandler;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.models.ViewWarehouse;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pa.iscde.commands.utils.Labels;
import pt.iscte.pidesco.extensibility.PidescoView;

/**
 * @author Fábio Martins
 * 
 * */
final public class CommandView implements PidescoView {

	private Text textField;
	private Tree commandTree;
	private Composite actionsArea;

	public static Composite viewArea;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new GridLayout(1, false));
		createSearchField(viewArea);
		createTreeTable(viewArea);
		createActionButtonsArea(viewArea);

		// This Method can only be called here, because it's when there are
		// already WorkbenchPages and stuff related to the UI
		ViewWarehouse.loadAllViews();

	}

	private void createTreeTable(Composite viewArea) {

		GridData gridData = new GridData();
		commandTree = new Tree(viewArea, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK);

		commandTree.setHeaderVisible(true);
		commandTree.setLinesVisible(true);
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		commandTree.setLayoutData(gridData);

		TreeColumn column1 = new TreeColumn(commandTree, SWT.NONE);
		column1.setText(Labels.CONTEXTDESCRIPTION_LBL);
		column1.setWidth(300);

		TreeColumn column2 = new TreeColumn(commandTree, SWT.NONE);
		column2.setText(Labels.COMMANDDESCRIPTION);
		column2.setWidth(200);

		TreeColumn column3 = new TreeColumn(commandTree, SWT.NONE);
		column3.setText(Labels.KEYESCRIPTION_LBL);
		column3.setWidth(100);

		addDataToTreeTable();

		commandTree.addListener(SWT.MeasureItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				event.height = 25;
			}
		});

		commandTree.addListener(SWT.Selection, new SelectionListener());
	}

	private void addDataToTreeTable() {

		Set<String> contexts = CommandWarehouse.getCommandsContext();
		String match = textField.getText();

		for (String context : contexts) {
			TreeItem item = new TreeItem(commandTree, SWT.NONE);
			item.setData(context);
			item.setText(new String[] { context, "" });
			item.setBackground(new Color(null, 240, 240, 240));

			List<CommandDefinition> commandsDefinitionList = CommandWarehouse
					.getCommandByContext(context);

			for (CommandDefinition command : commandsDefinitionList) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				subItem.setData(command);
				subItem.setText(new String[] {
						command.getCommandKey().getCommandName(),
						command.getDescription(),
						command.getCommandKey().toString() });
				subItem.getParentItem().setExpanded(true);

				if (match.length() != 0 && !subItem.getText().contains(match)) {
					subItem.dispose();
				}
			}

			if (item.getItems().length == 0) {
				item.dispose();
			}

		}

	}

	private void createSearchField(Composite viewArea) {
		textField = new Text(viewArea, SWT.BORDER);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.TOP;
		gridData.grabExcessVerticalSpace = false;
		textField.setLayoutData(gridData);

		textField.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				// TODO implementar um timer de key.
				commandTree.removeAll();
				addDataToTreeTable();

			}
		});
	}

	private void createActionButtonsArea(Composite viewArea) {
		actionsArea = new Composite(viewArea, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 0;
		fillLayout.spacing = 10;
		actionsArea.setLayout(fillLayout);

		ExtensionHandler handler = new ExtensionHandler();
		handler.setExtensionHandler(ExtensionPointsIDS.ACTION_ID.getID(),
				new ActionHandler(actionsArea, commandTree));
		handler.startProcessExtension();

	}

	private final class SelectionListener implements Listener {

		public void handleEvent(Event event) {
			if (event.detail == SWT.CHECK) {

				TreeItem item = (TreeItem) event.item;
				item.setGrayed(false);

				for (int j = 0; j < item.getItems().length; j++) {
					item.getItems()[j].setChecked(item.getChecked());
				}

				setGrayed(item);
			}
		}

		private void setGrayed(TreeItem selectedItem) {
			if (selectedItem.getParentItem() != null) {
				int numChecked = 0;
				TreeItem[] selectedItemParentItemChildren = selectedItem
						.getParentItem().getItems();
				for (int i = 0; i < selectedItemParentItemChildren.length; i++) {
					if (selectedItemParentItemChildren[i].getChecked())
						numChecked++;

				}

				if (numChecked != 0) {
					selectedItem.getParentItem().setChecked(true);
					processGrayed(selectedItem, numChecked,
							selectedItemParentItemChildren);
				} else {
					selectedItem.getParentItem().setChecked(false);
				}
			}
		}

		private void processGrayed(TreeItem selectedItem, int numChecked,
				TreeItem[] selectedItemParentItemChildren) {
			if (numChecked == selectedItemParentItemChildren.length) {
				selectedItem.getParentItem().setGrayed(false);
			} else {
				selectedItem.getParentItem().setGrayed(true);
			}
		}
	}
}
