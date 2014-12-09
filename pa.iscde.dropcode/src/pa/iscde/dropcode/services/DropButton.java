package pa.iscde.dropcode.services;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;

public interface DropButton {

	public Image getIcon();
	
	public void clicked(ASTNode node);
}
