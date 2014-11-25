package pa.iscde.commands.external.services;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import pt.iscte.pidesco.extensibility.PidescoView;

public class CommandView implements PidescoView {

	private Text textField;
	private Tree commandTree;
	private Composite actionsArea;
	private Button delete;
	private Button edit;
	private Button newCommand;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		viewArea.setLayout(new GridLayout(1, false));

		createSearchField(viewArea);
		createTreeTable(viewArea);
		createActionButtons(viewArea);

	}

	private void createTreeTable(Composite viewArea) {
		GridData gridData;
		commandTree = new Tree(viewArea, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.CHECK);

		commandTree.setHeaderVisible(true);
		commandTree.setLinesVisible(true);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		commandTree.setLayoutData(gridData);

		TreeColumn column1 = new TreeColumn(commandTree, SWT.NONE);
		column1.setText("Description");
		column1.setWidth(380);

		TreeColumn column2 = new TreeColumn(commandTree, SWT.NONE);
		column2.setText("Binding");
		column2.setWidth(80);

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
		for (int i = 0; i < 4; i++) {
			TreeItem item = new TreeItem(commandTree, SWT.NONE);

			item.setText(new String[] { "Context " + i, "" });
			item.setBackground(new Color(null, 240, 240, 240));

			for (int j = 0; j < 4; j++) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				subItem.setText(new String[] { "Command description " + j,
						"CTRL+R+" + j });
				subItem.getParentItem().setExpanded(true);
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
				System.out.println(textField.getText());
				// TODO Filtrar a lista

			}
		});
	}

	private void createActionButtons(Composite viewArea) {
		actionsArea = new Composite(viewArea, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 10;
		fillLayout.spacing = 10;
		actionsArea.setLayout(fillLayout);

		delete = new Button(actionsArea, SWT.PUSH);
		delete.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		delete.setText("Delete");

		edit = new Button(actionsArea, SWT.PUSH);
		edit.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		edit.setText("Edit");

		newCommand = new Button(actionsArea, SWT.PUSH);
		newCommand.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		newCommand.setText("New");

		Button combine = new Button(actionsArea, SWT.PUSH);
		combine.setCursor(new Cursor(null, SWT.CURSOR_HAND));
		combine.setText("Combine");
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
