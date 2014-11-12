package pt.iscte.pidesco.extensibility;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pt.iscte.pidesco.internal.PidescoActivator;

public enum PidescoExtensionPoint {
		VIEW,
		TOOL;
		
		private IExtensionRegistry reg = Platform.getExtensionRegistry();
		
		public String getId() {
			return PidescoActivator.PLUGIN_ID + "." + name().toLowerCase();
		}
		
		public IExtension[] getExtensions() {
			return reg.getExtensionPoint(getId()).getExtensions();
		}
	}