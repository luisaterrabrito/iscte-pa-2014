package pa.iscde.formulas;

import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.swt.events.SelectionListener;

import pa.iscde.formulas.listeners.CalculatorListener;
import pa.iscde.formulas.listeners.CodeEjectorListener;


public abstract class Formula{
	
	private SelectionListener currentListener;
	
	public abstract String[] inputs();
	public abstract String name();
	public abstract String result(String[] inputs);

	public String methodCode() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("formulas\\"+name()+".txt");
		
		String method = "";
		Scanner s = new Scanner(is);
			while(s.hasNext()){
				method+=s.nextLine()+"\n";
			}
		s.close();
		
		return method;
	}
	
	public SelectionListener getCalculatorListener() {
		currentListener = new CalculatorListener(this);
		return currentListener;
	}
	
	public SelectionListener getCodeEjectorListener() {
		currentListener = new CodeEjectorListener(this);
		return currentListener;
	}
	public SelectionListener getCurrentListener() {
		return currentListener;
	}
	

}
