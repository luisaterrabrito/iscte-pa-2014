package pa.iscde.configurator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import pa.iscde.configurator.ConfiguratorExtensionPoint;
import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
import pa.iscde.configurator.model.interfaces.PropertyProvider;

public class Constructor {

	public LinkedList<Component> createComponents() {
		// TODO Auto-generated method stub
		LinkedList<Component> componentList = new LinkedList<Component>();
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = reg
				.getExtensionPoint("pt.iscte.pidesco.view");

		
		componentList.add(new Component(extensionPoint.getContributor()
				.getName(), true));
		System.out.println("O PRINCIPAL: "
				+ extensionPoint.getContributor().getName());

		IExtension[] extensions = extensionPoint.getExtensions();

		for (IExtension ext : extensions) {
			IContributor contributor = ext.getContributor();
			componentList.add(new Component(contributor.getName(), true));
			System.out.println("O SECUNDARIO: " + contributor.getName());
			// Bundle b = Platform.getBundle(contributor.getName());

//			IConfigurationElement[] confs = ext.getConfigurationElements();
			
		}
		return componentList;

	}

	public LinkedList<Dependency> createDependencies(
			LinkedList<Component> components) {
		// TODO Auto-generated method stub
		LinkedList<Dependency> dependenciesList = new LinkedList<Dependency>();
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = reg
				.getExtensionPoint("pt.iscte.pidesco.view");
		IContributor mainContributor = extensionPoint.getContributor();
		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension ext : extensions) {
			IContributor contributor = ext.getContributor();

				dependenciesList.add(new Dependency(getComponentByName(
						components, mainContributor.getName()),
						getComponentByName(components, contributor.getName()),extensionPoint.getUniqueIdentifier()));
						
			IExtensionPoint otherCompExtPoints = reg
					.getExtensionPoint(contributor.getName());
			if (otherCompExtPoints != null) {
				IExtension[] otherCompExtensions = otherCompExtPoints
						.getExtensions();
				for (IExtension iExtension : otherCompExtensions) {
					IContributor otherContributors = iExtension
							.getContributor();
						dependenciesList.add(new Dependency(getComponentByName(
								components, contributor.getName()),
								getComponentByName(components,
										otherContributors.getName()),otherCompExtPoints.getUniqueIdentifier()));
					
				}
			}
		}
		return dependenciesList;
	}
	
	public HashMap<PropertyProvider, List<String>> createProperties()
	{
		HashMap<PropertyProvider, List<String>> ppiComponents = new HashMap<PropertyProvider, List<String>>();
		for (IExtension viewExtension : ConfiguratorExtensionPoint.PROPERTYPROVIDER
				.getExtensions()) {
			ArrayList<String> components = new ArrayList<String>();
			
			
			/*String pluginId = viewExtension.getContributor().getName();
			String viewId = viewExtension.getUniqueIdentifier();
			String viewTitle = viewExtension.getLabel();*/
			try {
				PropertyProvider ppi = (PropertyProvider)viewExtension.getConfigurationElements()[0].createExecutableExtension("class");
				for (IConfigurationElement component : viewExtension.getConfigurationElements()[0].getChildren())
				{
				components.add(component.getAttribute("id"));
				}
				
				ppiComponents.put(ppi, components);	
				
				
			} catch (InvalidRegistryObjectException | CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return ppiComponents;
	}

	private Component getComponentByName(LinkedList<Component> components,
			String name) {
		// TODO Auto-generated method stub
		for (Component component : components) {
			if (component.getName().equals(name))
				return component;
		}
		return null;

	}

}
