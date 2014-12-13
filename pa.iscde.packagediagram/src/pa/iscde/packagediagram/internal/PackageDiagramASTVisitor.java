package pa.iscde.packagediagram.internal;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;

/**
 * 
 * Is an extension to ASTVisitor to access javaeditor services
 *
 */

public class PackageDiagramASTVisitor extends ASTVisitor{

	
	private ArrayList<ImportDeclaration> importList = new ArrayList<ImportDeclaration>();
	
	
	private PackageDeclaration packageDeclaration;
	
	/**
	 * Add the file import declaration
	 * 
	 * @param node
	 * @return import.add(node
	 */
	public boolean visit(ImportDeclaration node){
		return importList.add(node);
	}
	
	/**
	 * Access to the file package
	 * 
	 * @param node
	 * @return true
	 */
	public boolean visit(PackageDeclaration node){
		packageDeclaration = node;
		return true;
	}

	/**
	 * Returns import list
	 * 
	 * @return importList
	 */
	public ArrayList<ImportDeclaration> getImportList() {
		return importList;
	}
	
	/**
	 * Returns packgeDeclaration
	 * 
	 * @return packageDeclaration
	 */
	public PackageDeclaration getPackageDeclaration() {
		return packageDeclaration;
	}
	

}
