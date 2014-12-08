package pa.iscde.commands.external.services;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.utils.Labels;

public class CommandViewTree {

	protected Text textField;
	protected Tree commandTree;
	private boolean useParentSelection;
	private int[] columsIndexToRemove;

	public CommandViewTree(Composite parentComposite,
			int... columsIndexToRemove) {
		useParentSelection = true;
		this.columsIndexToRemove = columsIndexToRemove;
		parentComposite.setLayout(new GridLayout(1, false));
		createSearchField(parentComposite);
		createTreeTable(parentComposite);
	}

	public CommandViewTree(Composite parentComposite,
			boolean useParentSelection, int... columsIndexToRemove) {
		this.useParentSelection = useParentSelection;
		this.columsIndexToRemove = columsIndexToRemove;
		parentComposite.setLayout(new GridLayout(1, false));
		createSearchField(parentComposite);
		createTreeTable(parentComposite);
	}

	private void createTreeTable(Composite parentComposite) {

		GridData gridData = new GridData();
		commandTree = new Tree(parentComposite, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK);

		commandTree.setHeaderVisible(true);
		commandTree.setLinesVisible(true);
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		commandTree.setLayoutData(gridData);

		addColumns();

		addDataToTreeTable();

		commandTree.addListener(SWT.MeasureItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				event.height = 25;
			}
		});

		if (useParentSelection)
			commandTree.addListener(SWT.Selection, new SelectionListener());
	}

	private void addColumns() {

		if (!asIndex(0)) {
			TreeColumn column1 = new TreeColumn(commandTree, SWT.NONE);
			column1.setText(Labels.CONTEXTDESCRIPTION_LBL);
			column1.setWidth(300);
		}

		if (!asIndex(1)) {
			TreeColumn column2 = new TreeColumn(commandTree, SWT.NONE);
			column2.setText(Labels.COMMANDDESCRIPTION);
			column2.setWidth(200);
		}

		if (!asIndex(2)) {
			TreeColumn column3 = new TreeColumn(commandTree, SWT.NONE);
			column3.setText(Labels.KEYESCRIPTION_LBL);
			column3.setWidth(100);
		}
	}

	private boolean asIndex(int i) {
		for (int j = 0; j < columsIndexToRemove.length; j++) {
			if (columsIndexToRemove[j] == i) {
				return true;
			}
		}

		return false;
	}

	protected void addDataToTreeTable() {

		Set<String> contexts = CommandWarehouse.getInstance()
				.getCommandsContext();
		String match = textField.getText();

		for (String context : contexts) {
			TreeItem item = new TreeItem(commandTree, SWT.NONE);
			item.setData(context);
			item.setText(new String[] { context, "" });
			item.setBackground(new Color(null, 240, 240, 240));

			List<CommandDefinition> commandsDefinitionList = CommandWarehouse
					.getInstance().getCommandByContext(context);

			for (CommandDefinition command : commandsDefinitionList) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				// O tree item agora observa o commandkey.
				// NOTA: O SWT não permite extender o TreeItem, dai não ser
				// possivel utilizar correctamente o padrão observador,
				// observado.
				// http://stackoverflow.com/questions/4264983/why-is-subclassing-not-allowed-for-many-of-the-swt-controls
				command.getCommandKey().addObserver(subItem);
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

	public int getNumSelectedItems() {
		int counter = 0;
		for (int i = 0; i < commandTree.getItems().length; i++) {
			if (commandTree.getItems()[i].getChecked()) {
				counter++;
			}
		}

		return counter;
	}

	private void createSearchField(Composite parentComposite) {
		textField = new Text(parentComposite, SWT.BORDER);
		textField.setMessage(Labels.SEARCHTEXT);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.TOP;
		gridData.grabExcessVerticalSpace = false;
		textField.setLayoutData(gridData);

		textField.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				commandTree.removeAll();
				addDataToTreeTable();

			}
		});
	}

	public Tree getCommandTree() {
		return commandTree;
	}

	public void refresh() {
		commandTree.removeAll();
		addDataToTreeTable();
	}

	final class SelectionListener implements Listener {

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
