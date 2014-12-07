package pa.iscde.stylechecker.model.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import pa.iscde.stylechecker.model.IStyleRule;
import pa.iscde.stylechecker.utils.SWTResourceManager;

public class StyleRuleTableView extends Composite {
	
	private Table tbRules;
	private Button btnRefresh;
	private Button btnClear;
	
	private static final String ACTIVE = "Active";
	private static final String STOPPED = "Stopped";
	private static final int NUM_COLUMNS = 4;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StyleRuleTableView(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 16, SWT.NORMAL));
		setLayout(new GridLayout(1, false));
		
		Group btnsGroup_tools = new Group(this, SWT.NONE);
		btnsGroup_tools.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		GridData gd_btnsGroup_tools = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnsGroup_tools.heightHint = 18;
		btnsGroup_tools.setLayoutData(gd_btnsGroup_tools);
		
		btnRefresh = new Button(btnsGroup_tools, SWT.NONE);
		btnRefresh.setBounds(10, 0, 95, 28);
		btnRefresh.setText("Refresh");
		
		btnClear = new Button(btnsGroup_tools, SWT.NONE);
		
		btnClear.setBounds(111, 0, 95, 28);
		btnClear.setText("Clear");
		
		tbRules = new Table(this, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		tbRules.setFont(SWTResourceManager.getFont(".Helvetica Neue DeskInterface", 11, SWT.NORMAL));
		tbRules.setDragDetect(false);
		tbRules.setSelection(0);
		tbRules.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tbRules.setHeaderVisible(true);
		tbRules.setLinesVisible(true);
		
		TableColumn tblclmnState = new TableColumn(tbRules, SWT.LEFT);
		tblclmnState.setMoveable(true);
		tblclmnState.setWidth(100);
		tblclmnState.setText("State");
		
		TableColumn tblclmnType = new TableColumn(tbRules, SWT.NONE);
		tblclmnType.setWidth(100);
		tblclmnType.setText("Type");
		
		TableColumn tblclmnName = new TableColumn(tbRules, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnViolactions = new TableColumn(tbRules, SWT.LEFT);
		tblclmnViolactions.setMoveable(true);
		tblclmnViolactions.setWidth(100);
		tblclmnViolactions.setText("# Violations");

	}
	
	public void addStyleRule(IStyleRule rule) {
		
	}
	
	public void addClearButtonSelectionListener(SelectionListener listener) {
		btnClear.addSelectionListener(listener);
	}
	
	public void addRefreshButtonSelectionListener(SelectionListener listener) {
		btnRefresh.addSelectionListener(listener);
	}
	
	public void packAll() {
		for (int i = 0; i < NUM_COLUMNS; i++) {
	        tbRules.getColumn(i).pack();
	      }
	}
	
	public void addRule(IStyleRule rule) {
		TableItem item = new TableItem(tbRules, SWT.NONE);
		item.setText(0, rule.getActive()?ACTIVE:STOPPED);
		item.setText(1, rule.getClass().getSimpleName());
		item.setText(2, rule.getDescription());
		item.setText(3, ""+rule.getViolations());
		packAll();
		tbRules.computeSize(SWT.FILL, SWT.FILL);
	}
	
	public void addRule(IStyleRule rule,String type) {
		TableItem item = new TableItem(tbRules, SWT.NONE);
		item.setText(0, rule.getActive()?ACTIVE:STOPPED);
		item.setText(1, type);
		item.setText(2, rule.getDescription());
		item.setText(3, ""+rule.getViolations());
		packAll();
		tbRules.computeSize(SWT.FILL, SWT.FILL);
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
}
