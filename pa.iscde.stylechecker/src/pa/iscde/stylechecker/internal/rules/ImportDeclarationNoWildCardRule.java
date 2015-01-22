package pa.iscde.stylechecker.internal.rules;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import pa.iscde.stylechecker.domain.Constant;


public class ImportDeclarationNoWildCardRule extends AbstractImportDeclarationRule {
	
	
	
	@Override
	public String getWarningMessage() {
		return Constant.IMPORT_STM_WILD_CARD_WARNING;
	}


	@Override
	public boolean check(ImportDeclaration node) {
			return node.toString().contains("*");
	}


	@Override
	public String getDescription() {
		return "Checking for wild card imports";
	}
	 

	
	
	
	

}
