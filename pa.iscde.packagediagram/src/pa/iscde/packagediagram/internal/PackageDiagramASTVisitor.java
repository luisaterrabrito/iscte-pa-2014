package pa.iscde.packagediagram.internal;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class PackageDiagramASTVisitor extends ASTVisitor{

	
	private ArrayList<ImportDeclaration> importList = new ArrayList<ImportDeclaration>();
	
	
	private PackageDeclaration packageDeclaration;
	
	

	public boolean visit(ImportDeclaration node){
		return importList.add(node);
	}
	
	public boolean visit(PackageDeclaration node){
		packageDeclaration = node;
		return true;
	}

	public ArrayList<ImportDeclaration> getImportList() {
		return importList;
	}
	
	public PackageDeclaration getPackageDeclaration() {
		return packageDeclaration;
	}
	

}
