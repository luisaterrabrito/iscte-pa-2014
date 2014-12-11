package pa.iscde.callgraph.dropcodeextension;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;

import pa.iscde.callgraph.MyView;
import pa.iscde.dropcode.services.DropButton;

/**
 * 
 * This extension allows the Dropcode view to interact with our Call Graph viewer by adding
 * action to their button. We receive the selected method and show its diagram.
 * 
 * 
 * @author Pedro Neves
 *
 */
public class dropcodeExtension implements DropButton{
	
	private Image extensionImage = MyView.getInstance().callGraphIcon;
	private String extensionName = "CallGraph Viewer";

	/**
	 * 
	 * @return the image to be shown in the button.
	 */
	@Override
	public Image getIcon() {
		return extensionImage;
	}

	/**
	 * 
	 * @return the text to be shown in the button.
	 */
	@Override
	public String getText() {
		return extensionName;
	}

	/**
	 * 
	 * @param node the node to be shown in the Call Graph viewer.
	 */
	@Override
	public void clicked(ASTNode node) {
//		MyView.getInstance().selectMethod(node);
	}
	

}
