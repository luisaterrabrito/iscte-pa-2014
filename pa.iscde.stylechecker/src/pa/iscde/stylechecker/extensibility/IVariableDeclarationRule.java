package pa.iscde.stylechecker.extensibility;

import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public interface IVariableDeclarationRule {
	
	public boolean check(VariableDeclarationStatement node) ;
	
	public String getWarningMessage() ;
	
}
