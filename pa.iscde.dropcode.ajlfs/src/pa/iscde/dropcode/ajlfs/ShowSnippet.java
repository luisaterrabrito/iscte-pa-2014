package pa.iscde.dropcode.ajlfs;

import javax.swing.JOptionPane;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;

import pa.iscde.dropcode.services.DropButton;

public class ShowSnippet implements DropButton {

	@Override
	public String getText() {
		return "Show Code";
	}

	@Override
	public Image getIcon() {
		return null;
	}

	@Override
	public void clicked(ASTNode node) {
		JOptionPane.showMessageDialog(null, new ConditionalExpression().getTargetSnippet());
	}

}
