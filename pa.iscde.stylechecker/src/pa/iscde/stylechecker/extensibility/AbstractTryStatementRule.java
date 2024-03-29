package pa.iscde.stylechecker.extensibility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TryStatement;

import pa.iscde.stylechecker.model.AbstractStyleRule;

/**
 * 
 * @author joaomarques and josevaz
 *
 */
public abstract class AbstractTryStatementRule extends AbstractStyleRule{
	
	@Override
	public boolean check(ASTNode node) {
		return check((TryStatement)node);
	}
	
	/**
	 * This method implement the rule logical.
	 * @param node the AST Node
	 * @return - true if the node does not violate the rule otherwise false
	 */
	public abstract boolean check(TryStatement node) ;
	
	/**
	 * @return warring message to be displayed on rule violations.
	 */
	public abstract String getWarningMessage() ;
	
}
