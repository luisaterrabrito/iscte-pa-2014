package pa.iscde.stylechecker.model.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import pa.iscde.stylechecker.domain.Constant;
import pa.iscde.stylechecker.model.AbstractStyleRule;
import pa.iscde.stylechecker.model.IStyleRule;
import pa.iscde.stylechecker.utils.SWTResourceManager;

public class StyleRuleTableView extends Composite {
	
	private Table tbRules;
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StyleRuleTableView(Composite parent, int style) {
		super(parent, style);
		
		
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

		
	public void packAll() {
		for (int i = 0; i < Constant.TABLE_VIEW_NUM_COLUMNS; i++) {
	        tbRules.getColumn(i).pack();
	      }
	}
	
	public void addRule(AbstractStyleRule rule) {
		TableItem item = new TableItem(tbRules, SWT.NONE);
		item.setText(0, rule.getActive()?Constant.RULE_STATE_ACTIVE:Constant.RULE_STATE_STOPPED);
		item.setText(1, rule.getClass().getSimpleName());
		item.setText(2, rule.getDescription());
		item.setText(3, ""+rule.getViolations());
		packAll();
		tbRules.computeSize(SWT.FILL, SWT.FILL);
	}
	
	public void addRule(IStyleRule dummyRule,String type) {
		TableItem item = new TableItem(tbRules, SWT.NONE);
		item.setText(0, dummyRule.getActive()?Constant.RULE_STATE_ACTIVE:Constant.RULE_STATE_STOPPED);
		item.setText(1, type);
		item.setText(2, dummyRule.getDescription()==null?"Dummy":dummyRule.getDescription());
		item.setText(3, ""+dummyRule.getViolations());
		packAll();
		tbRules.computeSize(SWT.FILL, SWT.FILL);
	}
	
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
}
