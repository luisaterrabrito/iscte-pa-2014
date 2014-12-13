package pa.iscte.mycomp;

import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import pt.iscte.pidesco.extensibility.PidescoView;

public class MyView implements PidescoView {

	private Graph graph;

	private LinkedList<GraphConnection> connections;
	private LinkedList<GraphNode> nodes;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		// TODO Auto-generated method stub
		graph = new Graph(viewArea, SWT.None);
		nodes = new LinkedList<GraphNode>();
		connections = new LinkedList<GraphConnection>();

		String[] name = { "Core", "Outline", "Package Explorer", "JUnit",
				"JavaDoc", "Debug" };
		for (int i = 0; i < name.length; i++) {
			nodes.add(new GraphNode(graph, SWT.None, name[i]));
		}

		connections.add(CreateConnection("Core", "Outline"));
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		graph.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(arg0);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private GraphConnection CreateConnection(String source, String destination) {
		// TODO Auto-generated method stub
		if (nodes.size() == 0) {
			throw new IllegalStateException("Nodes needed");
		}

		else {
			GraphNode src = null;
			GraphNode dest = null;
			for (GraphNode node : nodes) {
				if (node.getText().equals(source)) {
					src = node;
				} else if (node.getText().equals(destination)) {
					dest = node;
				}
			}
			if (src == null || dest == null) {
				throw new IllegalStateException(
						"At least one of the nodes doesn't exist");
			} else {
				return new GraphConnection(graph, SWT.None, src, dest);
			}
		}
	}

}
