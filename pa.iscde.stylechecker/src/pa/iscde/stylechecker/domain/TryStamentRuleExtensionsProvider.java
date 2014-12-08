package pa.iscde.stylechecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IExtension;

import pa.iscde.stylechecker.extensibility.AbstractTryStatementRule;
import pa.iscde.stylechecker.model.AbstractStyleRuleExensionProvider;


public class TryStamentRuleExtensionsProvider extends AbstractStyleRuleExensionProvider {
	
	
	private static final String TRY_STAMENT_EXT_POINT = null;

	public List<AbstractTryStatementRule> getExtentions() {
		List<AbstractTryStatementRule> rules = new ArrayList<AbstractTryStatementRule>();
		IExtension[] extensions = getExtentions(TRY_STAMENT_EXT_POINT);
		for (IExtension extension : extensions) {
			rules.add((AbstractTryStatementRule) extension);
		}
		return null;
		
	}

}
