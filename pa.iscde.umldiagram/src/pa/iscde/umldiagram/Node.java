package pa.iscde.umldiagram;

import org.eclipse.zest.core.widgets.GraphNode;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class Node {
	private GraphNode node;
	private String name;
	private SourceElement sourceElement;
	
	
	
	public Node(GraphNode node, String name, SourceElement sourceElement) {
		super();
		this.node = node;
		this.name = name;
		this.sourceElement = sourceElement;
	}
	
	public GraphNode getNode() {
		return node;
	}
	public String getName() {
		return name;
	}
	public SourceElement getSourceElement() {
		return sourceElement;
	}
	
	
	
}
