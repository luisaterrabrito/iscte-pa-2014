package pa.iscde.callgraph.extensibility;

import org.eclipse.zest.layouts.LayoutAlgorithm;

/**
 * 
 * This is the interface to be implemented for adding custom layouts to CallGraph
 * 
 * @author João Alves, Pedro Neves
 *
 */
public interface CustomLayout {
	
	/**
	 * 
	 * The method executed to get a custom layout for CallGraph
	 * 
	 * @returns the LayoutAltorithm object created by the extension
	 *
	 */
	LayoutAlgorithm getCustomLayout();

}
