package extensibility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;

public interface ButtonFilterProvider {

	public Image getButtonIcon();
	
	public boolean filterTree(ASTNode node); 
	
}
