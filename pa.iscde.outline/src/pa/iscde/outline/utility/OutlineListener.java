package pa.iscde.outline.utility;

import org.eclipse.jdt.core.dom.ASTNode;

public interface OutlineListener {

	void itemSelected(ASTNode node);
	
	public class Adapter implements OutlineListener {

		@Override
		public void itemSelected(ASTNode node) {
			
		}

		
	}

}
