package pa.iscde.callgraph.extensibility;

import java.util.ArrayList;  

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * This is the interface to be implemented for adding custom exports of CallGraph diagrams
 * 
 * @author João Alves
 *
 */
public interface ExportButton {

	/**
	 * 
	 * This is the interface to be implemented for adding custom layouts to CallGraph.
	 * Makes available the entire structure of the diagram and the Display object associated
	 * with the window.
	 * 
	 * @param selectedMethod (not-null)  Target method.
	 * @param aboveMethods (not-null) List of all the methods that call selectedMethod
	 * @param belowMethods (not-null) List of all the methods called by selectedMethod 
	 * @param (not-null) display the display in which the custom layout choose window is created
	 *
	 */
	void export(MethodDeclaration selectedMethod, ArrayList<MethodDeclaration> aboveMethods, 
			ArrayList<MethodInvocation> belowMethods, Display display);
	
}
	