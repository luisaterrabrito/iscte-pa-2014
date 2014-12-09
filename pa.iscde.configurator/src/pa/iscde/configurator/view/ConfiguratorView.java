package pa.iscde.configurator.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
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
	private HashMap<Component,GraphNode> componentNode;
	private HashMap<Dependency,GraphConnection> dependencyConnection;
	private Graph graph;
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		Display display = viewArea.getDisplay();
		Color white = new Color(display, 255,255,255);
		// TODO Auto-generated method stub
		
		
		viewArea.setLayout(new FillLayout());
		ScrolledComposite scroll = new ScrolledComposite(viewArea, SWT.H_SCROLL | SWT.V_SCROLL);
		
		Composite comp = new Composite(scroll, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setBackground(white);
		
		
		componentNode=new HashMap<Component, GraphNode>();
		dependencyConnection=new HashMap<Dependency, GraphConnection>();
		controller=new Controller();
		runningComponents=controller.getRunningComponents();
		graph= new Graph(comp, SWT.NONE);
		graph.setLayoutData(new GridData(750, 500));
		paintRunningComponents();
		dependencies = controller.getDependencies(runningComponents);
		paintGivenDependencies();
		
		Table table = new Table(comp, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
		        | SWT.H_SCROLL);
		    table.setHeaderVisible(true);
		    String[] titles = { "Col 1", "Col 2", "Col 3", "Col 4" };
		    
		    for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		        TableColumn column = new TableColumn(table, SWT.NULL);
		        column.setText(titles[loopIndex]);
		      }
		    
		    for (int loopIndex = 0; loopIndex < 24; loopIndex++) {
		        TableItem item = new TableItem(table, SWT.NULL);
		        item.setText("Item " + loopIndex);
		        item.setText(0, "Item " + loopIndex);
		        item.setText(1, "Yes");
		        item.setText(2, "No");
		        item.setText(3, "A table item");
		      }

		      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		        table.getColumn(loopIndex).pack();
		      }
		      
		table.setBounds(25, 25, 220, 200);
		table.setLayoutData(new GridData(600,150 ));

		    
		scroll.setContent(comp);
		scroll.setMinSize(300, comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		
		
		

		
		
		
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
