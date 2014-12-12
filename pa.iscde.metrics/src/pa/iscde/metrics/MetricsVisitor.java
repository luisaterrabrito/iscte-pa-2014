package pa.iscde.metrics;

import java.util.List;
import java.util.SortedSet;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class MetricsVisitor extends ASTVisitor {
	private int method_counter, line_counter, staticmethod_counter,
			package_counter, interface_counter, class_counter = 0;


	@Override
	public boolean visit(MethodDeclaration node) {

			method_counter++;

		// lineNumber = compilationUnit.getLineNumber(node.getStartPosition()) -
		// 1;

		if (Modifier.isStatic(node.getModifiers()))
			staticmethod_counter++;

		return false;
	};

	public boolean visit(CompilationUnit cu) {

		List classTypes = cu.types();
		for (Object object : classTypes) {

			switch (((AbstractTypeDeclaration)object).getNodeType()) {
			case ASTNode.ANONYMOUS_CLASS_DECLARATION:
				interface_counter++;
				break;

			case ASTNode.TYPE_DECLARATION:
				class_counter++;
				break;
			}
		}

		// cu.getLineNumber(cu.getStartPosition()-1);
		// line_counter++;
		// System.out.print(line_counter + ": ");
		// System.out.println(cu);

		 String[] lines = cu.toString().split("\n");
		 line_counter = lines.length;
//		 System.out.println("Lines: " + nrlines);
//		 for (int i = 0; i < lines.length; i++) {
//		 System.out.println(i+": "+lines[i]);
//		 }


		return true;
	}
	
	public boolean visit(PackageDeclaration node) {

		package_counter++;

		return false;
	};


	public int getMethodCounter() {
		return method_counter;
	}

	public int getLineCounter() {
		return line_counter;
	}

	public int getStaticMethods() {
		return staticmethod_counter;
	}


	public int getPackageCounter() {
		return package_counter;
	}
	
	public int getInterfaceCounter() {
		return interface_counter;
	}
	
	public int getClassCounter() {
		return class_counter;
	}

	public void visit(PackageElement root) {
		SortedSet<SourceElement> c = root.getChildren();
		for (SourceElement sourceElement : c) {
//			System.out.println(sourceElement.getClass());
		}
	}

}
