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
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength())
			insideClass = true;
		return true;
	}

	public boolean isInsideMethod() {
		return insideMethod;
	}

	public boolean isInsideClass() {
		return insideClass;
	}
}
