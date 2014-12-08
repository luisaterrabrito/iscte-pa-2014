package pa.iscde.stylechecker.extensibility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TryStatement;

import pa.iscde.stylechecker.model.AbstractStyleRule;

public abstract class AbstractTryStatementRule extends AbstractStyleRule{
	
	@Override
	public boolean check(ASTNode node) {
		return check((TryStatement)node);
	}
	
	public abstract boolean check(TryStatement node) ;
	
	public abstract String getWarningMessage() ;
	
}
