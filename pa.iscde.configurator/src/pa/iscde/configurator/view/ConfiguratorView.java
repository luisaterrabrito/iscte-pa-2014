package pa.iscde.configurator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import pa.iscde.configurator.controller.Controller;
import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
import pa.iscde.configurator.model.interfaces.DependencyStyle;
import pt.iscte.pidesco.extensibility.PidescoView;
/*
 * This class is used to represent the view of the bundle pa.iscde.configurator
 * 
 * When ConfiguratorView starts, it creates the controller to get information from him
 * about running components and dependencies between them. Next the view paints them.
 * 
 * It show a graph of all components and dependencies, and shows information about the components.
 * 
 */
public class ConfiguratorView implements PidescoView {
	private Controller controller;
	private LinkedList<Component> runningComponents;
	private LinkedList<Dependency> dependencies;
	private HashMap<Component, GraphNode> componentNode;
	private HashMap<Dependency, GraphConnection> dependencyConnection;
	private HashMap<String, String> propertyTable;
	private HashMap<DependencyStyle,String> styles;
	
	private Graph graph;
	private Table table;
	private Composite comp;
	private Combo combo;

	/*
	 * (non-Javadoc)
	 * @see pt.iscte.pidesco.extensibility.PidescoView#createContents(org.eclipse.swt.widgets.Composite, java.util.Map)
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		Display display = viewArea.getDisplay();
		final Color white = new Color(display, 255, 255, 255);
		// TODO Auto-generated method stub

		viewArea.setLayout(new FillLayout());
		ScrolledComposite scroll = new ScrolledComposite(viewArea, SWT.H_SCROLL
				| SWT.V_SCROLL);

		comp = new Composite(scroll, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setBackground(white);

		componentNode = new HashMap<Component, GraphNode>();
		dependencyConnection = new HashMap<Dependency, GraphConnection>();
		controller = new Controller();
		runningComponents = controller.getRunningComponents();
		styles = controller.getStyles();
		graph = new Graph(comp, SWT.NONE);
		graph.setLayoutData(new GridData(750, 500));
		
		
		createTable();
		createCombo();
		paintRunningComponents();
		dependencies = controller.getDependencies(runningComponents);
		paintGivenDependencies();
		

		scroll.setContent(comp);
		scroll.setMinSize(300, comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		
		
		

	}
	
	
	private void createTable()
	{

		table = new Table(comp, SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		table.setHeaderVisible(true);
		String[] titles = { "Propriedades", "Descrição" };

		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(table, SWT.NULL);
			column.setText(titles[loopIndex]);
		}
		
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			table.getColumn(loopIndex).pack();
		}

		table.setBounds(25, 25, 220, 200);
		table.pack();
		
		graph.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				
				if (((Graph) e.widget).getSelection().size()!=0)
				{
					graph.setSelection(new GraphItem[]{(GraphItem)e.item});
					Object selectedObject = ((Graph) e.widget).getSelection().get(0);
					GraphItem selectedItem = (GraphItem) selectedObject;
					propertyTable = controller.getProperties(selectedItem.getText());
					fillTable();
				}
				else
				{
					table.removeAll();
					table.pack();
				}
			}
		});
	}
	
	private void createCombo()
	{
		combo = new Combo(comp, SWT.READ_ONLY);
	    combo.setBounds(50, 50, 150, 65);
	    String items[] = styles.values().toArray(new String[styles.size()]);
	    combo.setItems(items);
	    combo.setLayoutData(new GridData(100, 500));
	    combo.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	        	for(Entry<DependencyStyle, String> entry : styles.entrySet()) {
	        		if (entry.getValue().equals(combo.getText()))
	        		{

	        				Set<Component> set = componentNode.keySet();
	        				ArrayList<Component> list = new ArrayList<Component>();
	        				list.addAll(set);
	        				if (!styles.isEmpty()) {
	        					for (Component c : list) {
	        				Color color=entry.getKey().getNodeColor(c.getName());
	        				componentNode.get(c).setBackgroundColor(color);
	        				color=entry.getKey().getSelectedNodeColor(c.getName());
	        				componentNode.get(c).setHighlightColor(color);
	        				
	        					}
	        					
	        					for (Dependency c : dependencies) {
	        						Color color=entry.getKey().getDependencyColor(c.getExtensionPointId());
	        						dependencyConnection.get(c).changeLineColor(color);
	        					}
	        			
	        		}

	        	}
	        }
	        }
	      });
	}
	
	
	private void fillTable()
	{
		table.removeAll();
		for (Entry<String, String> entry : propertyTable.entrySet())
			{
				TableItem item = new TableItem(table, SWT.NULL);
				item.setText(entry.getKey());
				item.setText(0, entry.getKey());
				item.setText(1, entry.getValue());
			}
		table.pack();
		
	}

	private void paintRunningComponents() {
		for (Component c : runningComponents) {
			GraphNode node = new GraphNode(graph, SWT.NONE, c.getName());
			node.setSize(-1, -1);
			componentNode.put(c, node);
		}
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}

	private void paintGivenDependencies() {
		// TODO Auto-generated method stub
		for (Dependency dependency : dependencies) {
			GraphNode main = componentNode.get(dependency.getMainComponent());
			GraphNode dependant = componentNode.get(dependency
					.getDependantComponent());
			GraphConnection conn = new GraphConnection(graph,
					ZestStyles.CONNECTIONS_DIRECTED, dependant, main);
			dependencyConnection.put(dependency, conn);
		}
	}

}
