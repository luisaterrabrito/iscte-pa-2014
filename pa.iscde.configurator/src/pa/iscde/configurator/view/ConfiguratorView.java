package pa.iscde.configurator.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import pa.iscde.configurator.controller.Controller;
import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
import pt.iscte.pidesco.extensibility.PidescoView;

public class ConfiguratorView implements PidescoView{
	private Controller controller;
	private LinkedList<Component> runningComponents;
	private LinkedList<Dependency> dependencies;
	private LinkedList<String> notRunningComponents;
	private Composite viewArea;
	private HashMap<Component,GraphNode> componentNode;
	private HashMap<Dependency,GraphConnection> dependencyConnection;
	private Graph graph;
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		// TODO Auto-generated method stub
		componentNode=new HashMap<Component, GraphNode>();
		dependencyConnection=new HashMap<Dependency, GraphConnection>();
		this.viewArea=viewArea;
		controller=new Controller();
		runningComponents=controller.getRunningComponents();
		graph= new Graph(viewArea, SWT.NONE);
		paintRunningComponents();
		dependencies = controller.getDependencies(runningComponents);
		paintGivenDependencies();

		
		
		
	}
	private void paintRunningComponents() {
		for (Component c : runningComponents) {
			GraphNode node=new GraphNode(graph, SWT.NONE, c.getName());
			componentNode.put(c, node);
		}
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(), true);
		graph.applyLayout();
		
	}
	private void paintGivenDependencies() {
		// TODO Auto-generated method stub
		for (Dependency dependency : dependencies) {
			GraphNode main=componentNode.get(dependency.getMainComponent());
			GraphNode dependant=componentNode.get(dependency.getDependantComponent());
			GraphConnection conn=new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, dependant, main);
			dependencyConnection.put(dependency, conn);
		}
	}
	


}
