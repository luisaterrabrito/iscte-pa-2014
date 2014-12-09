package pa.iscde.configurator.controller;

import java.util.LinkedList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;

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
						getComponentByName(components, contributor.getName())));
			
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
										otherContributors.getName())));
					
				}
			}
		}
		return dependenciesList;
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
