package pa.iscde.configurator.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
import pa.iscde.configurator.model.interfaces.PropertyProvider;

public class Controller {
	Constructor constructor;
	LinkedList<Component> components;
	LinkedList<Dependency> dependencies;
	HashMap<PropertyProvider, List<String>> properties;

	public Controller() {
		constructor = new Constructor();
		components = constructor.createComponents();
		properties = constructor.createProperties();
		dependencies = constructor.createDependencies(components);
	}

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

	public LinkedList<String> getNotRunningComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<Dependency> getAllDependencies() {
		// TODO Auto-generated method stub
		return null;
	}

}
