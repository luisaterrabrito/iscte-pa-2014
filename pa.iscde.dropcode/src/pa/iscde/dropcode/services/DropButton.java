package pa.iscde.dropcode.services;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;

public interface DropButton {

	/**
	 * This method defines the icon that will be used in the button.
	 * @return The icon of the button.
	 */
	
	public Image getIcon();
	
	/**
	 * This method defines the title that will be used in the button.
	 * @return The title of the button.
	 */
	public String getText();

	/**
	 * This method defines the action that will be performed by this button.
	 * @param node
	 */
	public void clicked(ASTNode node);
}
