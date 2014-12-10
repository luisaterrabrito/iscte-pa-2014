package pa.iscde.outline.utility;

import org.eclipse.jdt.core.dom.ASTNode;

public interface OutlineServices {

	ASTNode getSelectedNode();
	
	void selectNode(ASTNode node);
	
	void addListener(OutlineListener listener);

	void removeListener(OutlineListener listener);
}
