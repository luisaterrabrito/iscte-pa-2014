package pa.iscde.metrics;

import java.util.List;
import java.util.SortedSet;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class MetricsVisitor extends ASTVisitor {
	private int methodCounter, physicalLineCounter, logicalLineCounter, staticMethodCounter,
			packageCounter, interfaceCounter, classCounter, attributeCounter;
	private CompilationUnit compilationUnit;

	public boolean visit(MethodDeclaration node) {
			methodCounter++;

		this.physicalLineCounter = compilationUnit.getLineNumber(node
				.getStartPosition()) - 1;

		if (Modifier.isStatic(node.getModifiers()))
			staticMethodCounter++;

		return true;
	};

	public boolean visit(TypeDeclaration node) {
		if(node.isInterface())
			interfaceCounter++;
		else {
			classCounter++;
		}
		return true;
	}
	
	public boolean visit(FieldDeclaration node){
		System.out.println("attributes" );
		attributeCounter++;
		return true;
	}

	public boolean visit(CompilationUnit cu) {
		this.compilationUnit = cu;
		physicalLineCounter = cu.getLineNumber(cu.getLength()-1);
		logicalLineCounter = cu.toString().split("\n").length;
		return true;
	}

	public boolean visit(PackageDeclaration node) {
		System.out.println("package");
		packageCounter++;

		return true;
	};

	public int getMethodCounter() {
		return methodCounter;
	}

	public int getPhysicalLineCounter() {
		return physicalLineCounter;
	}

	public int getStaticMethods() {
		return staticMethodCounter;
	}

	public int getPackageCounter() {
		return packageCounter;
	}

	public int getInterfaceCounter() {
		return interfaceCounter;
	}

	public int getClassCounter() {
		return classCounter;
	}

	public int getLogicalLineCounter() {
		return logicalLineCounter;
	}

	public int getAttributeCounter() {
		return attributeCounter;
	}

	// public void visit(PackageElement root) {
	// SortedSet<SourceElement> c = root.getChildren();
	// for (SourceElement sourceElement : c) {
	// // System.out.println(sourceElement.getClass());
	// }
	// }

}
