package pa.iscde.stylechecker.model;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

public abstract class AbstractStyleRuleExensionProvider {
	
	public static IExtension[] getExtentions(String extPointId) {
		return Platform.getExtensionRegistry().getExtensionPoint(extPointId).getExtensions();		
	}

}
