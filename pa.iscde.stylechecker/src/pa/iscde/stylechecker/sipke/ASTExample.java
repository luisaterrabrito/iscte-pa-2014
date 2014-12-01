package pa.iscde.stylechecker.sipke;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TryStatement;

public class ASTExample extends ASTVisitor {

	@Override
	public boolean visit(ImportDeclaration node) {
		//node.getName().
		//regra()
	
		
		return super.visit(node);
	}

	@Override
	public boolean visit(TryStatement node) {
		//getTodosClassQueImplementam a tua interface
		//para cada invocar o check
		//setWarning
		//atualizar a lista
		
		return super.visit(node);
	}

	
	

}
