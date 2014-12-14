package pa.iscde.metrics.internal;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.metrics.extensibility.Metricable;

public class MetricsVisitor extends ASTVisitor {
	private int methodCounter, physicalLineCounter, logicalLineCounter, staticMethodCounter,
			packageCounter, interfaceCounter, classCounter, attributeCounter;
	private CompilationUnit compilationUnit;

	public boolean visit(MethodDeclaration node) {
	//methods
			methodCounter++;
		
	//static
		if (Modifier.isStatic(node.getModifiers()))
			staticMethodCounter++;

		return true;
	};
	
	//class or interface
	public boolean visit(TypeDeclaration node) {
		if(node.isInterface())
			interfaceCounter++;
	
		else {
			classCounter++;
		}
		
		return true;
	}
	
	//attributes
	public boolean visit(FieldDeclaration node){
		attributeCounter++;
		return true;
	}
	
	//lines of code
	public boolean visit(CompilationUnit cu) {
		this.compilationUnit = cu;
		physicalLineCounter += cu.getLineNumber(cu.getLength()-1);
		logicalLineCounter += cu.toString().split("\n").length;
		return true;
	}

	//packages
	public boolean visit(PackageDeclaration node) {
		packageCounter++;

		// public void visit(PackageElement root) {
		// SortedSet<SourceElement> c = root.getChildren();
		// for (SourceElement sourceElement : c) {
		// // System.out.println(sourceElement.getClass());
		// }
		// }
		
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


}
