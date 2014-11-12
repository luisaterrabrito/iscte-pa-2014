package pt.iscte.pidesco.app.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import pt.iscte.pidesco.extensibility.PidescoUI;
import pt.iscte.pidesco.extensibility.PidescoView;

public class PidescoActivator extends AbstractUIPlugin {

	public static final String PLUGIN_ID;
	
	static {
		PLUGIN_ID = FrameworkUtil.getBundle(PidescoActivator.class).getSymbolicName();
	}
	
	public enum ExtensionPoint {
		VIEW,
		TOOL,
		TEAM;
		
		private IExtensionRegistry reg = Platform.getExtensionRegistry();
		
		public String getId() {
			return PLUGIN_ID + "." + name().toLowerCase();
		}
		
		public IExtension[] getExtensions() {
			return reg.getExtensionPoint(getId()).getExtensions();
		}
	}
	
	// singleton instance
	private static PidescoActivator plugin;
	
	public static PidescoActivator getInstance() {
		return plugin;
	}
	
	private Map<String, ViewComponent> components;
	
	static class ViewComponent implements Comparable<ViewComponent>, PidescoView {
		final PidescoView component;
		final String pluginId;
		final String viewId;
		final String viewTitle;
		final ImageDescriptor icon;
		
		ViewComponent(PidescoView component, String pluginId, String viewId, String viewTitle, ImageDescriptor icon) {
			this.component = component;
			this.pluginId = pluginId;
			this.viewId = viewId;
			this.viewTitle = viewTitle;
			this.icon = icon;
		}
		
		static String secondaryId(String pluginId, String viewId) {
			return pluginId + "." + viewId;
		}
		
		String getSecondaryId() {
			return viewId;
		}
		
		
		@Override
		public int compareTo(ViewComponent o) {
			return viewTitle.compareTo(o.viewTitle);
		}

		@Override
		public void createContents(Composite viewArea, Map<String, Image> reg) {
			component.createContents(viewArea, reg);
		}
	}
	
	
	
	private void loadPlugins() throws CoreException {
		components = new HashMap<String, ViewComponent>();
		
		for(IExtension viewExtension : ExtensionPoint.VIEW.getExtensions()) {
			String pluginId = viewExtension.getContributor().getName();
			String viewId = viewExtension.getUniqueIdentifier();
			String viewTitle = viewExtension.getLabel();
			
			IConfigurationElement comp = viewExtension.getConfigurationElements()[0];
			PidescoView c = (PidescoView) comp.createExecutableExtension("class");
			String iconPath = PidescoUI.IMAGES_FOLDER + "/" + comp.getAttribute("icon");
			ImageDescriptor icon = null;
			if(iconPath != null && !iconPath.isEmpty()) {
				try {
					icon = imageDescriptorFromPlugin(pluginId, iconPath);
				}
				catch(RuntimeException e) {
					logPluginError(pluginId, "could not load view icon '" + iconPath + "'");
				}
			}
				
			ViewComponent vc = new ViewComponent(c, pluginId, viewId, viewTitle, icon);
			components.put(viewId, vc);
		}
	}

		
	void logPluginError(String pluginId, String message) {
		System.err.println(pluginId + ": " + message);
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		loadPlugins();
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	

	ViewComponent getComponent(String viewId) {
		return components.get(viewId);
	}
	
	

	public static ImageDescriptor getImageDescriptor(String path) {
		return getImageDescriptor(PLUGIN_ID, path);
	}

	public static ImageDescriptor getImageDescriptor(String pluginId, String path) {
		return imageDescriptorFromPlugin(pluginId, path);
	}
	
	public Collection<ViewComponent> getComponents() {
		return Collections.unmodifiableCollection(components.values());
	}

	
	
}
