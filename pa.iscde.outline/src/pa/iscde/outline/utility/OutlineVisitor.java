package pa.iscde.outline.utility;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import extensibility.ButtonFilterProvider;

public class OutlineVisitor extends ASTVisitor {
	private static final String TREE_NODE_TEXT_BASE = "%s%s : %s";
	private static final String VOID = "void";
	private TreeItem currentTreeItem;
	private boolean isAtRoot;
	private ArrayList<ButtonFilterProvider> activeButtonFilters;
	private Map<String, Image> imageMap;

	public OutlineVisitor(TreeItem rootItem,
			Map<String, Image> imageMap, ArrayList<ButtonFilterProvider> activeButtonFilters) {
		this.currentTreeItem = rootItem;
		this.activeButtonFilters = activeButtonFilters;
		this.isAtRoot = true;
		this.imageMap = imageMap;
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		Type fieldType = node.getType();
		Object o = node.fragments().get(0);
		if(o != null && o instanceof VariableDeclarationFragment) {
			String name = ((VariableDeclarationFragment)o).getName().toString();
			if(!addTreeNode(String.format(TREE_NODE_TEXT_BASE, name, "", fieldType), node)){
				return false;
			}
		}

		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		Type returnType = node.getReturnType2();
		String returnTypeName = "";
		if(returnType == null){
			returnTypeName = VOID;
		} else {
			returnTypeName = returnType.toString();
		}
		//at the 3rd argument for the String.format should be included the method's arguments list instead of ""
		if(!addTreeNode(String.format(TREE_NODE_TEXT_BASE, node.getName().toString(), "", returnTypeName), node)){
			return false;
		}
		
		return super.visit(node);
	}

	/**
	 * Visits a Type
	 * Adds a new TreeNode to include the Type's members
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		if(!verifyFilters(node)) {
			return false;
		}
		
		if(!isAtRoot){
			currentTreeItem = new TreeItem(currentTreeItem, SWT.NONE);
		} else {
			isAtRoot = false;
		}
		
		currentTreeItem.setText(node.getName().toString());
		currentTreeItem.setImage(imageMap.get("class_obj.png"));

		return super.visit(node);
	}

	/**
	 * Finishes a visit to a Type by moving back to it's parentItem
	 */
	@Override
	public void endVisit(TypeDeclaration node) {
		currentTreeItem = currentTreeItem.getParentItem();

		super.endVisit(node);
	}


	/**
	 * Adds a new TreeNode to the current TreeNode
	 * @param name to set as the TreeNode's text
	 */
	private boolean addTreeNode(String name, ASTNode node) {
		if(verifyFilters(node)){
			//TODO method to obtain the image, now it is NULL
			//Image image = getImage(node);
			TreeItem aux = new TreeItem(currentTreeItem, SWT.NONE);
			aux.setText(name);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param node to verify against the filtering
	 * @return
	 */
	private boolean verifyFilters(ASTNode node){
		for(ButtonFilterProvider filter : activeButtonFilters){
			if(!filter.filterTree(node)){
				return false;
			}
		}
		
		return true;
	}
	
//	private Image getImage(ASTNode node){
//		return null; //return image for ????
//	}
}
