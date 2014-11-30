package pa.iscde.configurator.controller;

import java.util.LinkedList;

import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;

public class Controller {
	Constructor constructor;
	LinkedList<Component> components;
	LinkedList<Dependency> dependencies;

	public Controller() {
		constructor = new Constructor();
		components = constructor.createComponents();
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
