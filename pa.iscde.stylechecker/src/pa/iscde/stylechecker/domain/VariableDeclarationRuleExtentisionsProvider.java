package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.extensibility.AbstractVariableDeclarationRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;

public class VariableDeclarationRuleExtentisionsProvider extends
		AbstractStyleRuleExensionProvider {
	

	public static List<AbstractVariableDeclarationRule> getExtentions() {
		List<AbstractVariableDeclarationRule> rules = new ArrayList<AbstractVariableDeclarationRule>();
		try {
		IExtension[] extensions = getExtentions(Constant.EXT_POINT_VARIABLE_STMT);
		for (IExtension extension : extensions) {
			for (IConfigurationElement config : extension.getConfigurationElements()) {			
					AbstractVariableDeclarationRule rule = (AbstractVariableDeclarationRule) config.createExecutableExtension(Constant.EXT_POINT_VARIABLE_STMT_ATRB_CLASS);
					rules.add(rule);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rules;
	}
	
	public static List<AbstractVariableDeclarationRule> getInternalRules() {
		List<AbstractVariableDeclarationRule> rules = new ArrayList<AbstractVariableDeclarationRule>();
		//TODO
		return rules;
	}

}
