package pa.iscde.umldiagram;

import java.util.ArrayList;

import org.eclipse.zest.core.widgets.GraphNode;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class Node {
	private GraphNode node;
	private String name;
	private SourceElement sourceElement;
	private ArrayList<String> fields = new ArrayList<String>();
	private ArrayList<String> classInstances = new ArrayList<String>();
	private String superC= new String(" ");
	
	public ArrayList<String> getClassInstances() {
		return classInstances;
	}

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

	public void setClassInstances(ArrayList<String> classInstances) {
		this.classInstances = classInstances;
	}

	public void setSuperClass(String string) {
		this.superC=string;
		
	}

	public String getSuperC() {
		return superC;
	}

	
	
}
