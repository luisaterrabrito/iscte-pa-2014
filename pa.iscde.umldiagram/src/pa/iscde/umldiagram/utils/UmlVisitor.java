package pa.iscde.umldiagram.utils;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
public class UmlVisitor extends ASTVisitor{

	private static ArrayList<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private static UmlVisitor instance;
	
	public UmlVisitor(){
		instance=this;
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		System.out.println(node.getName());
		methods.add(node);
		return false;
	}
	
	public ArrayList<MethodDeclaration> getMethods(){
		return methods;
	}
	
	public static UmlVisitor getInstance(){
		for (int i = 0; i < methods.size(); i++) {
			methods.remove(i);
		}
		return instance;
	}
}
