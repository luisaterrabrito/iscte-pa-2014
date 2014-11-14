package pa.iscde.formulas.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import pa.iscde.formulas.Formula;

public class CodeEjectorListener implements SelectionListener {
	private Formula formula;
	
	public CodeEjectorListener(Formula formula) {
		this.formula=formula;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		System.out.println(formula.methodCode());
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

}
