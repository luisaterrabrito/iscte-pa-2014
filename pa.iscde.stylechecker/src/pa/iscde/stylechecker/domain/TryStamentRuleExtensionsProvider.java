package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.extensibility.AbstractTryStatementRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;


public class TryStamentRuleExtensionsProvider extends AbstractStyleRuleExensionProvider {
	

	public static List<AbstractTryStatementRule> getExtentions() {
		List<AbstractTryStatementRule> rules = new ArrayList<AbstractTryStatementRule>();
		try {
			IExtension[] extensions = getExtentions(Constant.EXT_POINT_TRY_CATCH_STM);
			if (extensions == null ) 
				return rules;
			for (IExtension extension : extensions) {
				for (IConfigurationElement config : extension.getConfigurationElements()) {			
						AbstractTryStatementRule rule = (AbstractTryStatementRule) config.createExecutableExtension(Constant.EXT_POINT_TRY_CATCH_STM_ATRB_CLASS);
						rules.add(rule);
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return rules;
	}
	
	public static List<AbstractTryStatementRule> getInternalRules() {
		List<AbstractTryStatementRule> rules = new ArrayList<AbstractTryStatementRule>();
		//rules.add(new ImportDeclarationNoWildCardRule());
		return rules;
	}

}
