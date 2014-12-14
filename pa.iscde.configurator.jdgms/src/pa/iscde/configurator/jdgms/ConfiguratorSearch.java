package pa.iscde.configurator.jdgms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.ProjectBrowserSearchProvider;
import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.filtersearch.view.SearchCategory;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ConfiguratorSearch implements SearchProvider {
	private JavaEditorServices editorServices;
	private PidescoServices pidescoServices;
	private HashMap<IContributor, List<IExtensionPoint>> components;
	private HashMap<IExtensionPoint, String> classes;
	
	public ConfiguratorSearch()
	{
		
		
		components = new HashMap<IContributor, List<IExtensionPoint>>();
		classes = new HashMap<IExtensionPoint, String>();
		this.setComponents();
		
		
		Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		
		ServiceReference<JavaEditorServices> serviceReference_javaEditor = context.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(serviceReference_javaEditor);
		
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
	}
	
	@Override
	public List<Object> getResults(String text) {
		LinkedList<Object> hits = new LinkedList<Object>();
		for (Entry<IContributor, List<IExtensionPoint>> entry : components.entrySet())
		{
			hits.add(entry.getKey().getName());
			for (IExtensionPoint ext : entry.getValue())
			{
				hits.add(ext.getUniqueIdentifier());
				if (this.getClasses(ext)!=null)
				hits.add(this.getClasses(ext));
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
			
			for (Entry<IExtensionPoint, String> entry : classes.entrySet())
			{
				if (entry.getValue().equals(object))
				{
					return pidescoServices.getImageFromPlugin("pa.iscde.configurator.jdgms", "classes.gif");
				}
			}
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		SourceElement element = (SourceElement) object;
		File f = element.getFile();
		editorServices.openFile(f);
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
	
	private String getClasses(IExtensionPoint ext)
	{
		if (ext.getConfigurationElements()[0].getAttribute("class")!=null)
		{
			classes.put(ext, ext.getConfigurationElements()[0].getAttribute("class"));
			return ext.getConfigurationElements()[0].getAttribute("class");
		}
	return null;
	}
	
	
}
