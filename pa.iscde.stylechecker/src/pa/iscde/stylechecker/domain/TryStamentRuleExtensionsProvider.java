package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.extensibility.AbstractTryStatementRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;


public class TryStamentRuleExtensionsProvider extends AbstractStyleRuleExensionProvider {
	

	public static List<AbstractTryStatementRule> getExtentions() {
		List<AbstractTryStatementRule> rules = new ArrayList<AbstractTryStatementRule>();
		IExtension[] extensions = getExtentions(Constant.EXT_POINT_TRY_CATCH_STM);
		for (IExtension extension : extensions) {
			rules.add((AbstractTryStatementRule) extension);
		}
		return rules;
	}
	
	public static List<AbstractTryStatementRule> getInternalRules() {
		List<AbstractTryStatementRule> rules = new ArrayList<AbstractTryStatementRule>();
		//rules.add(new ImportDeclarationNoWildCardRule());
		return rules;
	}

}
