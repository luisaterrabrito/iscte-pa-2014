package pa.iscde.configurator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import pa.iscde.configurator.ConfiguratorExtensionPoint;
import pa.iscde.configurator.controller.Controller;
import pa.iscde.configurator.model.Component;
import pa.iscde.configurator.model.Dependency;
import pa.iscde.configurator.model.interfaces.DependencyStyle;
import pa.iscde.configurator.model.interfaces.PropertyProvider;
import pa.iscde.configurator.model.interfaces.PropertyProviderImpl;
import pt.iscte.pidesco.extensibility.PidescoExtensionPoint;
import pt.iscte.pidesco.extensibility.PidescoView;

public class ConfiguratorView implements PidescoView {
	private Controller controller;
	private LinkedList<Component> runningComponents;
	private LinkedList<Dependency> dependencies;
	private LinkedList<String> notRunningComponents;
	private HashMap<Component, GraphNode> componentNode;
	private HashMap<Dependency, GraphConnection> dependencyConnection;
	private Graph graph;

	private List<DependencyStyle> styles;
	private Map<String, List<PropertyProvider>> providers;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		Display display = viewArea.getDisplay();
		final Color white = new Color(display, 255, 255, 255);
		// TODO Auto-generated method stub

		styles = new Vector<DependencyStyle>();
		providers = new HashMap<String, List<PropertyProvider>>();
		viewArea.setLayout(new FillLayout());
		ScrolledComposite scroll = new ScrolledComposite(viewArea, SWT.H_SCROLL
				| SWT.V_SCROLL);

		Composite comp = new Composite(scroll, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setBackground(white);

		componentNode = new HashMap<Component, GraphNode>();
		dependencyConnection = new HashMap<Dependency, GraphConnection>();
		controller = new Controller();
		runningComponents = controller.getRunningComponents();
		graph = new Graph(comp, SWT.NONE);
		graph.setLayoutData(new GridData(750, 500));
		for (IExtension dependencyExtension : ConfiguratorExtensionPoint.DEPENDENCYSTYLE
				.getExtensions()) {
			try {
				DependencyStyle ds = (DependencyStyle) dependencyExtension
						.getConfigurationElements()[0]
						.createExecutableExtension("class");
				styles.add(ds);

			} catch (InvalidRegistryObjectException | CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		paintRunningComponents();
		ColorNodes();
		dependencies = controller.getDependencies(runningComponents);
		paintGivenDependencies();
		ColorDependencies();
		graph.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				System.out.println(((Graph) e.widget).getSelection());

			}

		});

		for (IExtension viewExtension : ConfiguratorExtensionPoint.PROPERTYPROVIDER
				.getExtensions()) {
			String pluginId = viewExtension.getContributor().getName();
			String viewId = viewExtension.getUniqueIdentifier();
			String viewTitle = viewExtension.getLabel();

			try {
				PropertyProvider ppi = (PropertyProvider) viewExtension
						.getConfigurationElements()[0]
						.createExecutableExtension("class");
				Table table = new Table(comp, SWT.BORDER | SWT.V_SCROLL
						| SWT.H_SCROLL);
				table.setHeaderVisible(true);
				String[] titles = { "Propriedades", "Descrição" };

				for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
					TableColumn column = new TableColumn(table, SWT.NULL);
					column.setText(titles[loopIndex]);
				}

				for (String string : ppi.getProperties()) {
					TableItem item = new TableItem(table, SWT.NULL);
					item.setText(string);
					item.setText(0, string);
					item.setText(1, ppi.getValue(string));
				}

				for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
					table.getColumn(loopIndex).pack();
				}

				table.setBounds(25, 25, 220, 200);
				table.setLayoutData(new GridData(600, 150));
			} catch (InvalidRegistryObjectException | CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * PropertyProviderImpl ppi = new PropertyProviderImpl();
		 */

		scroll.setContent(comp);
		scroll.setMinSize(300, comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);

	}

	private void ColorNodes() {
		Set<Component> set = componentNode.keySet();
		ArrayList<Component> list = new ArrayList<Component>();
		list.addAll(set);
		if (!styles.isEmpty()) {
			for (Component c : list) {
				Color color=styles.get(0).getNodeColor(c.getName());
				componentNode.get(c).setBackgroundColor(color);
			}
		}
	}

	private void ColorDependencies() {
		// TODO Auto-generated method stub

	}

	private void paintRunningComponents() {
		for (Component c : runningComponents) {
			GraphNode node = new GraphNode(graph, SWT.NONE, c.getName());
			componentNode.put(c, node);
		}

		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(), true);
		graph.applyLayout();

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
