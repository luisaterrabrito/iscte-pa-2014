package pa.iscde.configurator.controller;

import java.util.LinkedList;

import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
/*
 * This class creates the model classes that represent the components and dependencies to paint
 * 
 * This class is created by the controller and uses a class Platform to get the information
 * (components, dependencies and their attributes) from.
 */
public class Constructor {
	
	/*
	 * This method creates all the components related to pt.iscte.pidesco that 
	 * have a view and are running
	 * 
	 * @return the list of components created 
	 */
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

			// IConfigurationElement[] confs = ext.getConfigurationElements();

		}
		return componentList;

	}

	/*
	 * This method creates all the dependencies related to the components passed by parameter 
	 * Only creates dependencies if the main component or the dependent exist on the list.
	 * 
	 * @param List with all the components which matter to create dependencies
	 * 
	 * @return the list of dependencies created 
	 */
	public LinkedList<Dependency> createDependencies(
			LinkedList<Component> components) {
		// TODO Auto-generated method stub
		LinkedList<Dependency> dependenciesList = new LinkedList<Dependency>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint[] extensionPoints = extensionRegistry
				.getExtensionPoints();
		for (Component c : components) {
			for (IExtensionPoint ep : extensionPoints) {
				if (ep.getUniqueIdentifier().contains(c.getName() + "." + ep.getSimpleIdentifier())) {
					IExtension[] exts = ep.getExtensions();
					for (IExtension iExtension : exts) {
						IContributor cont = iExtension.getContributor();
						if (getComponentByName(components, cont.getName()) != null)
						{
							dependenciesList.add(new Dependency(c,
									getComponentByName(components,
											cont.getName()), ep
											.getUniqueIdentifier()));
						}
					}
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
