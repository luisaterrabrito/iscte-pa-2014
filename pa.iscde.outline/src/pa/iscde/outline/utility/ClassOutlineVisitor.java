package pa.iscde.outline.utility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ClassOutlineVisitor extends ASTVisitor {
	//private OurTree tree;
	
	@Override
	public void preVisit(ASTNode node) {
		// TODO Auto-generated method stub
		super.preVisit(node);
	}
	
	@Override
	public void postVisit(ASTNode node) {
		// TODO Auto-generated method stub
		super.postVisit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
			String name = node.getName().toString();
			System.out.println("method: " + name );
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
//		if(node complies to the filters)
//			tree.add(node);
		Object o = node.fragments().get(0);
		if(o != null && o instanceof VariableDeclarationFragment){
			String name = ((VariableDeclarationFragment)o).getName().toString();
			System.out.println("variable: " + name );
		}
		// TODO Auto-generated method stub
		return super.visit(node);
	}
	
	
}
