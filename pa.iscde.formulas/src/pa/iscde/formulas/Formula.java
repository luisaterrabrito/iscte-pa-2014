package pa.iscde.formulas;

import org.eclipse.swt.events.SelectionListener;

public interface Formula {	
	
	public String name();	
	public String methodCode();
	public String[] inputs();
	public String result(String[] inputs);
	public SelectionListener getListener();
	
}
