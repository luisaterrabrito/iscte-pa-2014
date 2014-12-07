package pa.iscde.snippets.extensionhandlers;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.snippets.external.CursorContext;

public class ContextVisitor extends ASTVisitor {
	private int cursorPosition;
	private boolean topClassVisited = false;
	private boolean outsideTopClass = false;
	private boolean insideMethod = false;
	private boolean insideIf = false;
	private boolean insideTry = false;
	private boolean insideCatch = false;
	private boolean insideFor = false;
	private boolean isAbstract = false;
	private boolean isFinal = false;
	private boolean isStatic = false;
	private boolean isInterface = false;
	private boolean isNative = false;
	private boolean isTransient = false;
	private boolean isSynchronized = false;
	private boolean isVolatile = false;
	private String visibility = "public";

	public ContextVisitor(int cursorPosition) {
		this.cursorPosition = cursorPosition;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition >= cursorPosition && !topClassVisited) {
			outsideTopClass = true;
			topClassVisited = true;
		} else {
			if (nodePosition < cursorPosition
					&& cursorPosition < nodePosition + node.getLength())
				treatModifiers(node.getModifiers());
		}
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength()) {
			insideMethod = true;
			treatModifiers(node.getModifiers());
		}
		return false;
	}

	@Override
	public boolean visit(IfStatement node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength())
			insideIf = true;
		return true;
	}

	@Override
	public boolean visit(TryStatement node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength()) {
			insideTry = true;
		}
		return true;
	}

	@Override
	public boolean visit(CatchClause node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength()) {
			insideCatch = true;
		}
		return true;
	}

	@Override
	public boolean visit(ForStatement node) {
		int nodePosition = node.getStartPosition() - 1;
		if (nodePosition < cursorPosition
				&& cursorPosition < nodePosition + node.getLength()) {
			insideFor = true;
		}
		return true;
	}

	private void treatModifiers(int modifiers) {
		if (Modifier.isAbstract(modifiers))
			isAbstract = true;
		if (Modifier.isFinal(modifiers))
			isFinal = true;
		if (Modifier.isStatic(modifiers))
			isStatic = true;
		if (Modifier.isInterface(modifiers))
			isInterface = true;
		if (Modifier.isNative(modifiers))
			isNative = true;
		if (Modifier.isTransient(modifiers))
			isTransient = true;
		if (Modifier.isSynchronized(modifiers))
			isSynchronized = true;
		if (Modifier.isVolatile(modifiers))
			isVolatile = true;
		if (Modifier.isPublic(modifiers))
			visibility = "public";
		if (Modifier.isProtected(modifiers))
			visibility = "protected";
		if (Modifier.isPrivate(modifiers))
			visibility = "private";
	}

	public CursorContext buildCursorContext(String openedFileExtension,
			String snippetLanguage) {
		return new CursorContext(openedFileExtension, snippetLanguage,
				outsideTopClass, insideMethod, insideIf, insideTry,
				insideCatch, insideFor, isAbstract, isFinal, isStatic,
				isInterface, isNative, isTransient, isSynchronized, isVolatile,
				visibility);
	}
}
