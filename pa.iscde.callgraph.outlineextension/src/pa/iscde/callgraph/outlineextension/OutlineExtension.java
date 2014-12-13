package pa.iscde.callgraph.outlineextension;

import org.eclipse.jdt.core.dom.ASTNode;

import pa.iscde.callgraph.services.CallGraphServices;
import pa.iscde.outline.utility.OutlineListener;

/**
 * 
 * This extension allows the Outline view to interact with our Call Graph viewer.
 * 
 * @author João Alves
 *
 */
public class OutlineExtension implements OutlineListener {
	
	/**
	 * 
	 * @param node the node to be shown in the Call Graph viewer.
	 * @param node the node to be printed 
	 */
	@Override
	public void itemSelected(ASTNode node) {
		CallGraphServices.selectMethod(node);
	}

}
