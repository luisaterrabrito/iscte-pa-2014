package pa.iscde.stylechecker.extensibility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import pa.iscde.stylechecker.model.AbstractStyleRule;

/**
 * TODO explain this class and the sub-class "contract"  
 * @author joaomarques and josevaz
 *
 */
public abstract class AbstractVariableDeclarationRule extends AbstractStyleRule{
			
	@Override
	public boolean check(ASTNode node) {
		return check((VariableDeclarationStatement)node);
	}
	
	/**
	 * This method implement the rule logical.
	 * @param node the AST Node
	 * @return - true if the node does not violate the rule otherwise false
	 */
	public abstract boolean check(VariableDeclarationStatement node) ;
	
	/**
	 * @return warring message to be displayed on rule violations.
	 */
	public abstract String getWarningMessage() ;
	
}
