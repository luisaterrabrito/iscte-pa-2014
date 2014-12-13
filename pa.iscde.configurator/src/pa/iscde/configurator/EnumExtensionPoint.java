package pa.iscde.configurator;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/*
 This class contains the enumerator with the names of the extensionPoints we created 
 on pa.iscde.configurator plug-in

 */
public enum EnumExtensionPoint {
	PROPERTYPROVIDER, DEPENDENCYSTYLE;

	private IExtensionRegistry reg = Platform.getExtensionRegistry();
	/*
	 * This method is used to get the Id of some extension point specified in the enumerator
	 * 
	 * The extensionPoint id on the manifest must be in lower case for this to work
	 * 
	 * @return String with the full id
	 */
	public String getId() {
		return Activator.PLUGIN_ID + "." + name().toLowerCase();
	}
	/*
	 * This method is used to get all the extensions of some extension point 
	 * specified in the enumerator
	 * 
	 * @return an array with all the Extension of the Extension Point
	 */
	public IExtension[] getExtensions() {
		return reg.getExtensionPoint(getId()).getExtensions();
	}
}