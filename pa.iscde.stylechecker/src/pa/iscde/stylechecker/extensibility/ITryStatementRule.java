package pa.iscde.stylechecker.extensibility;

import org.eclipse.jdt.core.dom.TryStatement;

public interface ITryStatementRule {
	
	public boolean check(TryStatement node) ;
	
	public String getWarningMessage() ;
	
}
