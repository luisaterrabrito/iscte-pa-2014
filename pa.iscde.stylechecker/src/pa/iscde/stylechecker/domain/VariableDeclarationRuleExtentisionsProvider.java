package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.extensibility.AbstractVariableDeclarationRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;

public class VariableDeclarationRuleExtentisionsProvider extends
		AbstractStyleRuleExensionProvider {
	

	public static List<AbstractVariableDeclarationRule> getExtentions() {
		List<AbstractVariableDeclarationRule> rules = new ArrayList<AbstractVariableDeclarationRule>();
		IExtension[] extensions = getExtentions(Constant.EXT_POINT_VARIABLE_STM);
		for (IExtension extension : extensions) {
			rules.add((AbstractVariableDeclarationRule) extension);
		}
		return rules;
	}
	
	public static List<AbstractVariableDeclarationRule> getInternalRules() {
		List<AbstractVariableDeclarationRule> rules = new ArrayList<AbstractVariableDeclarationRule>();
		//rules.add(new ImportDeclarationNoWildCardRule());
		return rules;
	}

}
