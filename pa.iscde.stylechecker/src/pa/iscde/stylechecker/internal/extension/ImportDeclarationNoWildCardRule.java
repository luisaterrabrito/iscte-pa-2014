package pa.iscde.stylechecker.internal.extension;

import org.eclipse.jdt.core.dom.ImportDeclaration;


public class ImportDeclarationNoWildCardRule extends AbstractImportDeclarationRule {
	
	private static final String WILD_CARD_WARNING = " Upss gra*** Wild Card tho' ? ";
	
	
	@Override
	public String getWarningMessage() {
		return WILD_CARD_WARNING;
	}


	@Override
	public boolean check(ImportDeclaration node) {
			System.out.println(node.getName());
			return !node.getName().getFullyQualifiedName().contains("*");
	}
	 

	
	
	
	

}
