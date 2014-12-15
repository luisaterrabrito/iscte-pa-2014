package pa.iscde.outline.utility;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;

import extensibility.ButtonFilterProvider;

public class OutlineVisitor extends ASTVisitor {
	List<String> names = new ArrayList<String>();
	private TreeItem currentTreeItem;
	private boolean isAtRoot;

	public OutlineVisitor(TreeItem rootItem,
			ArrayList<ButtonFilterProvider> activeButtonFilters) {
		this.currentTreeItem = rootItem;
		this.isAtRoot = true;
	}

	public List<String> getNames() {
		return names;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		addTreeNode("method: " + node.getName().toString());
		return super.visit(node);
	}

	/**
	 * Visits a Type
	 * Adds a new TreeNode to include the Type's members
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		if(!isAtRoot){
			currentTreeItem = new TreeItem(currentTreeItem, SWT.NONE);
		} else {
			isAtRoot = false;
		}
		
		currentTreeItem.setText("Class: " + node.getName().toString());

		return super.visit(node);
	}

	/**
	 * Finish a visit to a Type by moving back to it's parentItem
	 */
	@Override
	public void endVisit(TypeDeclaration node) {
		currentTreeItem = currentTreeItem.getParentItem();

		super.endVisit(node);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		Object o = node.fragments().get(0);
		if(o != null && o instanceof VariableDeclarationFragment) {
			String name = ((VariableDeclarationFragment)o).getName().toString();
			addTreeNode(name);
		}

		return super.visit(node);
	}

	/**
	 * Adds a new TreeNode to the current TreeNode
	 * @param name to set as the TreeNode's text
	 */
	private void addTreeNode(String name) {
		TreeItem aux = new TreeItem(currentTreeItem, SWT.NONE);
		aux.setText(name);
	}
}
