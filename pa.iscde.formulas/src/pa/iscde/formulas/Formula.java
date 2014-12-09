package pa.iscde.formulas;


import org.eclipse.swt.events.SelectionListener;

import pa.iscde.formulas.listeners.CalculatorListener;
import pa.iscde.formulas.listeners.CodeEjectorListener;
import pa.iscde.formulas.util.ReadUtil;


/**
 * Its a generic class used for all formulas.
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public abstract class Formula{
	
	private SelectionListener currentListener;
	
	/**
	 * Set formula inputs number
	 * @return inputs
	 */
	public abstract String[] inputs();
	/**
	 * Set formula name
	 * @return name
	 */
	public abstract String name();
	
	/**
	 * Set the code for the calculator mode
	 * 
	 * @param inputs
	 * @return result
	 */
	public abstract String result(String[] inputs);

	/**
	 * Opens the file that contains the formula code.
	 * @return method
	 */
	public String methodCode(String file) {
		return ReadUtil.read(file);
	}
	
	/**
	 * Initializes the listener for this formula as Calculator mode.
	 * @return currentListener
	 */
	public SelectionListener getCalculatorListener() {
		currentListener = new CalculatorListener(this);
		return currentListener;
	}
	
	/**
	 * Initializes the listener for this formula as Code Ejector mode.
	 * @return currentListener
	 */
	public SelectionListener getCodeEjectorListener() {
		currentListener = new CodeEjectorListener(this);
		return currentListener;
	}
	
	/**
	 * Returns the listener of this formula.
	 * @return currentListener
	 */
	public SelectionListener getCurrentListener() {
		return currentListener;
	}
	

}
