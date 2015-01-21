package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.internal.extension.AbstractImportDeclarationRule;
import pa.iscde.stylechecker.internal.extension.ImportDeclarationNoWildCardRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;

public class ImportDeclarationRuleExtentisionsProvider extends
		AbstractStyleRuleExensionProvider {
	
	private static final String DEFAULT_INTERNAL_EXTENSION_PACKAGE = "pa.iscde.stylechecker.internal.extension";
	private static final String IMPORT_DECLARATION_EXT_POINT = "pa.iscde.stylechecker.importstyle";//TODO;

	public static List<AbstractImportDeclarationRule> getExtentions() {
		List<AbstractImportDeclarationRule> rules = new ArrayList<AbstractImportDeclarationRule>();
		IExtension[] extensions = getExtentions(IMPORT_DECLARATION_EXT_POINT);
		for (IExtension extension : extensions) {
			rules.add((AbstractImportDeclarationRule) extension);
		}
		return rules;
	}
	
	public static List<AbstractImportDeclarationRule> getInternalExtentions() {
		List<AbstractImportDeclarationRule> rules = new ArrayList<AbstractImportDeclarationRule>();
		rules.add(new ImportDeclarationNoWildCardRule());
		return rules;
	}

}
