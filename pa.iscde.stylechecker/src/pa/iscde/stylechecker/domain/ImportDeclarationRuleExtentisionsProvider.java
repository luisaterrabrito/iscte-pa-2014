package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.internal.rules.AbstractImportDeclarationRule;
import pa.iscde.stylechecker.internal.rules.ImportDeclarationNoWildCardRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;

public class ImportDeclarationRuleExtentisionsProvider extends
		AbstractStyleRuleExensionProvider {
	

	public static List<AbstractImportDeclarationRule> getExtentions() {
		List<AbstractImportDeclarationRule> rules = new ArrayList<AbstractImportDeclarationRule>();
		IExtension[] extensions = getExtentions(Constant.EXT_POINT_IMPORT_STM);
		for (IExtension extension : extensions) {
			rules.add((AbstractImportDeclarationRule) extension);
		}
		return rules;
	}
	
	public static List<AbstractImportDeclarationRule> getInternalRules() {
		List<AbstractImportDeclarationRule> rules = new ArrayList<AbstractImportDeclarationRule>();
		rules.add(new ImportDeclarationNoWildCardRule());
		return rules;
	}

}
