package pa.iscde.snippets.extensionhandlers;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ContextVisitor extends ASTVisitor {
	private int cursorPosition;
	private boolean insideMethod = false;
	private boolean insideClass = false;

	public ContextVisitor(int cursorPosition) {
		this.cursorPosition = cursorPosition;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength())
			insideMethod = true;
		return false;
	}

	@Override
	public boolean visit(CompilationUnit node) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isInsideMethod() {
		return insideMethod;
	}

	public void setInsideMethod(boolean insideMethod) {
		this.insideMethod = insideMethod;
	}

	public boolean isInsideClass() {
		return insideClass;
	}

	public void setInsideClass(boolean insideClass) {
		this.insideClass = insideClass;
	}
	
	
	
}
