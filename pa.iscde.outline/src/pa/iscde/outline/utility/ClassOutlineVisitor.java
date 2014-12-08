package pa.iscde.outline.utility;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ClassOutlineVisitor extends ASTVisitor {
	//private OurTree tree;
	
	List<String> names = new ArrayList<String>();
	
	@Override
	public void preVisit(ASTNode node) {
		// TODO Auto-generated method stub
		super.preVisit(node);
	}
	
	public List<String> getNames() {
		return names;
	}

	@Override
	public void postVisit(ASTNode node) {
		// TODO Auto-generated method stub
		super.postVisit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		
		names.add("method: " + node.getName().toString());
//			String name = node.getName().toString();
//			System.out.println("method: " + name );
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		names.add("Class: " + node.getName().toString());
//		String name = node.getName().toString();
//		System.out.println("class: " + name );
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
//		if(node complies to the filters)
//			tree.add(node);
		Object o = node.fragments().get(0);
		if(o != null && o instanceof VariableDeclarationFragment){
			String name = ((VariableDeclarationFragment)o).getName().toString();
			names.add("var: " + name);
		}
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	
	
}
