package pa.iscde.umldiagram.drbps.utils;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
public class UmlVisitor extends ASTVisitor{

	private ArrayList<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private ArrayList<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
	private ArrayList<EnumDeclaration> enums = new ArrayList<EnumDeclaration>();
	private ArrayList<String> classInstances = new ArrayList<String>();
	private String superClass;
	private ArrayList<String> implementClasses = new ArrayList<String>();
	private boolean isInterface = false;



	public String getSuperClass() {
		return superClass;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		if(node.getSuperclassType()!=null){
			superClass=node.getSuperclassType().toString();
		}
		if(node.isInterface()){
			isInterface=true;
		}
		
		if(node.superInterfaceTypes()!=null){
			for (int i = 0; i <node.superInterfaceTypes().size(); i++) {
				String e = node.superInterfaceTypes().get(i).toString();
				implementClasses.add(e);
			}
				
		}
		return true;
	}
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
	public ArrayList<String> getImplementClasses() {
		return implementClasses;
	}
	public boolean isInterface() {
		return isInterface;
	}
	
	

}
