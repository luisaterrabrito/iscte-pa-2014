package pa.iscde.umldiagram;

import java.util.ArrayList;

import org.eclipse.zest.core.widgets.GraphNode;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class Node {
	private GraphNode node;
	private String name;
	private SourceElement sourceElement;
	private ArrayList<String> fields = new ArrayList<String>();
	
	
	
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
	
	public void addField(String f){
		fields.add(f);
	}

	public ArrayList<String> getFields() {
		return fields;
	}
	
	
	
}
