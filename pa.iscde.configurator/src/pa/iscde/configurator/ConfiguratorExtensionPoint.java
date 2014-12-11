package pa.iscde.configurator;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public enum ConfiguratorExtensionPoint {
	PROPERTYPROVIDER,DEPENDENCYSTYLE;
	
	private IExtensionRegistry reg = Platform.getExtensionRegistry();
	
	public String getId() {
		return Activator.PLUGIN_ID + "." + name().toLowerCase();
	}
	
	public IExtension[] getExtensions() {
		return reg.getExtensionPoint(getId()).getExtensions();
	}
}