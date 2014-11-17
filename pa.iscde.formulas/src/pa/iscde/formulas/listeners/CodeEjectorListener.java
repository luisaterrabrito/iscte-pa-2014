package pa.iscde.formulas.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import pa.iscde.formulas.Formula;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeEjectorListener implements SelectionListener {
	private Formula formula;
	private JavaEditorServices javaeditor;
	
	public CodeEjectorListener(Formula formula) {
		this.formula=formula;
	}
	
	public void setTarget(JavaEditorServices javaeditor){
		this.javaeditor=javaeditor;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		javaeditor.insertTextAtCursor(formula.methodCode());
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}


}
