package pa.iscde.configurator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

import pa.iscde.configurator.EnumExtensionPoint;
import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
import pa.iscde.configurator.model.interfaces.DependencyStyle;
import pa.iscde.configurator.model.interfaces.PropertyProvider;
/*
 * This class is used as an intermediary between the view and the data classes
 * 
 * Represents the Controller on MVC architecture
 * 
 * It is created when the view is opened and gives the view the classes of data to represent. 
 */
public class Controller {
	Constructor constructor;
	LinkedList<Component> components;
	LinkedList<Dependency> dependencies;
	HashMap<PropertyProvider, List<String>> properties;
	HashMap<DependencyStyle, String> styles;
	/*
	 * Constructor of the class Controller
	 * when created, it starts the Constructor and get the data from him.
	 * It also gets all the classes that are extending pa.iscde.configurator extension points
	 */
	public Controller() {
		constructor = new Constructor();
		components = constructor.createComponents();
		properties = loadProperties();
		dependencies = constructor.createDependencies(components);
		styles = loadStyles();

		
	}
	/*
	 * This method is called by the view and is used to get all the running components to paint
	 * It asks Constructor for them
	 * 
	 * @return a list with all the components to paint
	 */
	public LinkedList<Component> getRunningComponents() {
		// TODO Auto-generated method stub
		LinkedList<Component> running = new LinkedList<Component>();
		;
		for (Component component : components) {
			if (component.isRunning())
				running.add(component);
		}
		return running;
	}

	/*
	 * This method is called by the view and is used to get all the dependencies, associated with
	 * the running components, to paint
	 * It asks Constructor for them
	 * 
	 * @param a list of all the components that dependencies must be associated with 
	 * 
	 * @return a list with all the dependencies to paint
	 */
	public LinkedList<Dependency> getDependencies(
			LinkedList<Component> runningComponents) {
		// TODO Auto-generated method stub
		LinkedList<Dependency> relatedDependencies = new LinkedList<Dependency>();
		for (Dependency dependency : dependencies) {
			if (thereIsComponentWithName(runningComponents,
					dependency.getMainComponent())
					&& thereIsComponentWithName(runningComponents,
							dependency.getDependantComponent())) {

				relatedDependencies.add(dependency);
			}
		}
		return relatedDependencies;
	
	}

	/*
	 * This method is used to get the hashmap with the classes that were used to extend the
	 * extension point pa.iscde.configurator.dependencystyle
	 * 
	 * @return a hashmap with the styles
	 */
	public HashMap<DependencyStyle, String> getStyles()
	{
		return styles;
	}
	/*
	 * This method is used to get the hashmap with the properties and values to insert 
	 * on the table of the given component
	 * 
	 * @param a String with the name of the selected component to get the information for
	 * 
	 * @return a hashmap with the properties and their values
	 */
	public HashMap<String,String> getProperties(String selectedComponent)
	{
		HashMap<String,String> properties = new HashMap<String,String>();
		for (Entry<PropertyProvider, List<String>> entry : this.properties.entrySet())
		{
			if (entry.getValue().contains(selectedComponent))
			{
				for (String property : entry.getKey().getProperties())
				{
					properties.put(property,entry.getKey().getValue(property));
				}
			}
		}
		return properties;
	}

	

	private boolean thereIsComponentWithName(
			LinkedList<Component> runningComponents, Component mainComponent) {
		// TODO Auto-generated method stub
		for (Component component : runningComponents) {
			if(component.getName().equals(mainComponent.getName()))
				return true;
		}
		return false;
	}

	
	private HashMap<PropertyProvider, List<String>> loadProperties()
	{
		HashMap<PropertyProvider, List<String>> ppiComponents = new HashMap<PropertyProvider, List<String>>();
		for (IExtension viewExtension : EnumExtensionPoint.PROPERTYPROVIDER
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
	/*
	 * This method is used to check what classes are being used to extend the extension point
	 * pa.iscde.configurator.dependencystyle and creat an hashmap with that information
	 * 
	 * @return a hashmap with the classes that are implementing DependencyStyle and the name to appear on the combo box (ConfiguratorView)
	 */
	public HashMap<DependencyStyle,String> loadStyles()
	{
		HashMap<DependencyStyle,String> styles = new HashMap<DependencyStyle,String>();
		for (IExtension viewExtension : EnumExtensionPoint.DEPENDENCYSTYLE
				.getExtensions()) {
			
			
			/*String pluginId = viewExtension.getContributor().getName();
			String viewId = viewExtension.getUniqueIdentifier();
			String viewTitle = viewExtension.getLabel();*/
			try {
				DependencyStyle ds = (DependencyStyle)viewExtension.getConfigurationElements()[0].createExecutableExtension("class");
				
				styles.put(ds, ds.getName());
				
				
			} catch (InvalidRegistryObjectException | CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return styles;
	}

}
