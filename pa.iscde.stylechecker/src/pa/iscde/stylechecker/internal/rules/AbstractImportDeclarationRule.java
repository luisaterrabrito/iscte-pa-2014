package pa.iscde.stylechecker.internal.rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import pa.iscde.stylechecker.model.AbstractStyleRule;

public abstract class AbstractImportDeclarationRule extends AbstractStyleRule{
	
	@Override
	public boolean check(ASTNode node) {
		return check((ImportDeclaration)node);
	}
	
	public abstract boolean check(ImportDeclaration node) ;
	
	public abstract String getWarningMessage() ;
	
}
