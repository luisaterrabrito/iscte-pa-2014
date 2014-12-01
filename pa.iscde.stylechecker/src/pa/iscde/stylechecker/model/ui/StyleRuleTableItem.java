package pa.iscde.stylechecker.model.ui;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import pa.iscde.stylechecker.model.IStyleRule;

public class StyleRuleTableItem extends TableItem {
	
	private static final String ACTIVE = "Active";
	private static final String STOPPED = "Stopped";
	private IStyleRule rule;
	
	public StyleRuleTableItem(Table parent, int style, IStyleRule rule) {
		super(parent, style);
		this.rule = rule;
		initTableItem();
	}

	private void initTableItem() {
		this.setText(0, rule.getActive()?ACTIVE:STOPPED);
		this.setText(1, rule.getDescription());
		this.setText(2, rule.getDescription());
		this.setText(3, ""+rule.getViolations());
	}

	
	public void clean() {
		this.setText(3, "");
	}
	
}
