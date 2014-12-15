package pa.iscde.configurator.jdgms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;


import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.ProjectBrowserSearchProvider;
import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;

public class ConfiguratorSearch implements SearchProvider {
	private PidescoServices pidescoServices;
	private HashMap<IContributor, List<IExtensionPoint>> components;
	
	public ConfiguratorSearch()
	{
		
		
		components = new HashMap<IContributor, List<IExtensionPoint>>();
		this.setComponents();
		
		
		Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
	
		
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
	}
	
	@Override
	public List<Object> getResults(String text) {
		LinkedList<Object> hits = new LinkedList<Object>();
		for (Entry<IContributor, List<IExtensionPoint>> entry : components.entrySet())
		{
			if (entry.getKey().getName().contains(text))
			{
				hits.add(entry.getKey().getName());
			}
			for (IExtensionPoint ext : entry.getValue())
			{
				if (ext.getUniqueIdentifier().contains(text))
				{
					hits.add(ext.getUniqueIdentifier());
				}
			}
		}
		
		return hits;
	}


	@Override
	public Image setImage(Object object) {
			for (Entry<IContributor, List<IExtensionPoint>> entry : components.entrySet())
			{
			if (entry.getKey().getName().equals(object))
			{
				return pidescoServices.getImageFromPlugin("pa.iscde.configurator.jdgms", "plugin.gif");
			}

				for (IExtensionPoint ext : entry.getValue())
				{
					if (ext.getUniqueIdentifier().equals(object))
					{
						return pidescoServices.getImageFromPlugin("pa.iscde.configurator.jdgms", "ext_point_obj.gif");
					}
			}
			}
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		
	}
	
	private void setComponents()
	{
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = reg
				.getExtensionPoint("pt.iscte.pidesco.view");
		this.setExtensions(extensionPoint.getContributor());
		
		IExtension[] extensions = extensionPoint.getExtensions();

		for (IExtension ext : extensions) {
			IContributor contributor = ext.getContributor();
			this.setExtensions(contributor);
			
		}
	}
	
	private void setExtensions(IContributor contributor)
	{
		List<IExtensionPoint> list = new LinkedList<IExtensionPoint>();
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint[] extensionPoints = extensionRegistry
				.getExtensionPoints();
			for (IExtensionPoint ep : extensionPoints) {
				if (ep.getUniqueIdentifier().equals(contributor.getName() + "." + ep.getSimpleIdentifier()) && ep!=null) {
					list.add(ep);
				}
				}
			components.put(contributor, list);
	}
	
	
}
