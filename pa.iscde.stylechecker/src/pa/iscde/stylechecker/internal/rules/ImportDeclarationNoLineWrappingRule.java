package pa.iscde.stylechecker.internal.rules;

import org.eclipse.jdt.core.dom.ImportDeclaration;

import pa.iscde.stylechecker.domain.Constant;

public class ImportDeclarationNoLineWrappingRule extends AbstractImportDeclarationRule {

	@Override
	public String getDescription() {
		return "Line Wrapping imports";
	}

	@Override
	public boolean check(ImportDeclaration node) {
		return node.getLength() > node.toString().length();
	}

	@Override
	public String getWarningMessage() {
		return Constant.IMPORT_STM_LINE_WRAPP_WARNING;
	}

}
