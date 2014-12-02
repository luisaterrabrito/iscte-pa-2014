package pa.iscde.umldiagram.utils;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
public class UmlVisitor extends ASTVisitor{

	private ArrayList<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private ArrayList<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
	private ArrayList<EnumDeclaration> enums = new ArrayList<EnumDeclaration>();
	private ArrayList<String> classInstances = new ArrayList<String>();
	
	@Override
	public boolean visit(MethodDeclaration m) {
		methods.add(m);
		return true;
	}




	@Override
	public boolean visit(ClassInstanceCreation c) {
		classInstances.add(c.getType().toString());
		return true;
	}




	@Override
	public boolean visit(EnumDeclaration e) {
		enums.add(e);
		return true;
	}



	@Override
	public boolean visit(FieldDeclaration f) {
		fields.add(f);
		return true;
	}

	public ArrayList<EnumDeclaration> getEnums() {
		return enums;
	}

	public ArrayList<MethodDeclaration> getMethods(){
		return methods;
	}
	
	public ArrayList<FieldDeclaration> getFields(){
		return fields;
	}
	public ArrayList<String> getClassInstances() {
		return classInstances;
	}
}
