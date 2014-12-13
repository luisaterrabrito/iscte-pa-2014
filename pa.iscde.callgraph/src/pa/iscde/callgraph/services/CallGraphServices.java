package pa.iscde.callgraph.services;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;

import pa.iscde.callgraph.CallGraphView;


/**
 * 
 * This class is intented to provide connection between our implementations of
 * other's extension points and our own plugin.
 * 
 * @author Pedro Neves, João Alves
 *
 */
public class CallGraphServices {
	
	
	/**
	 * 
	 * Retrieve the current instance of CallGraph view.
	 * 
	 * @return CallGraphView instance
	 */
	public static CallGraphView getViewInstance(){
		return CallGraphView.getInstance();
	}

	/**
	 * 
	 * Retrieve the image associated to the current instance of CallGraph view.
	 * 
	 * @return CallGraph icon
	 */
	public static Image getCallGraphIcon(){
		return CallGraphView.getInstance().getCallGraphIcon();
	}
	
	/**
	 * 
	 * Show CallGraph diagram associated with given method
	 * 
	 * @param node the node referring to the method to be shown
	 */
	public static void selectMethod(ASTNode node){
		 CallGraphView.getInstance().selectMethod(node);
	}
}
