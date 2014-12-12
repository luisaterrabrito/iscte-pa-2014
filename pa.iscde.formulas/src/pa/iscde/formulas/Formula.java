package pa.iscde.formulas;


import org.eclipse.swt.events.SelectionListener;

import pa.iscde.formulas.listeners.CalculatorListener;
import pa.iscde.formulas.listeners.CodeEjectorListener;
import pa.iscde.formulas.util.FileReadUtil;
import pa.iscde.formulas.view.FormulasView;


/**
 * Its a generic class used for all formulas and as a extention point.
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public abstract class Formula{
	
	private String pluginID = FormulasView.PLUGIN_ID;
	private String file = "formulas\\"+name()+".txt";
	
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
	public String methodCode() {
		return FileReadUtil.read(pluginID,file);
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
	 * File could be from a diffrent plugin
	 * 
	 * @param pluginID
	 */
	public void setPluginID(String pluginID){
		this.pluginID=pluginID;
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
	
	/**
	 * Set file name
	 * 
	 * @param method_file
	 */
	public void setFile(String method_file) {
		file=method_file;
	}
	

}
