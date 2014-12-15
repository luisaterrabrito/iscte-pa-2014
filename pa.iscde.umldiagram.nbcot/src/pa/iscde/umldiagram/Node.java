package pa.iscde.umldiagram;

import java.util.ArrayList;

import org.eclipse.zest.core.widgets.GraphNode;

import pa.iscde.umldiagram.UmlTheme.ClassType;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class Node {
	private GraphNode node;
	private String name="";
	private SourceElement sourceElement;
	private ArrayList<String> fields = new ArrayList<String>();
	private ArrayList<String> classInstances = new ArrayList<String>();
	private String superC= new String(" ");
	private ArrayList<String> implementClasses = new ArrayList<String>();
	private ClassType type;
	private UMLClassFigure figure;
	
	public ArrayList<String> getClassInstances() {
		return classInstances;
	}

	public ClassType getType() {
		return type;
	}

	public Node(GraphNode node, String name, SourceElement sourceElement, UMLClassFigure figure) {
		this.node = node;
		this.name = name;
		this.figure=figure;
		this.sourceElement = sourceElement;
	}
	
	public UMLClassFigure getFigure() {
		return figure;
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
	
	public void setImplementClasses(ArrayList<String> implementClasses){
		this.implementClasses=implementClasses;
	}
	
	public ArrayList<String> getImplementClasses() {
		return implementClasses;
	}

	public String getSuperC() {
		return superC;
	}

	public void setType(ClassType type) {
		this.type=type;
		
	}

	
	
}
