package pa.iscde.stylechecker.model;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

public abstract class AbstractStyleRuleExensionProvider {
	
	public IExtension[] getExtentions(String extensionId) {
		return Platform.getExtensionRegistry().getExtensions(extensionId);		
	}

}
