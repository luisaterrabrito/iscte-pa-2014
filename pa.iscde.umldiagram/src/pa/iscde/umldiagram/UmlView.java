package pa.iscde.umldiagram;


import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.zest.core.widgets.CGraphNode;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.umldiagram.utils.UmlVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * @author Nuno e Diogo
 */
public class UmlView implements PidescoView {
	private static UmlView umlView;
	private Graph umlGraph;
	private Bundle bundle = FrameworkUtil.getBundle(UmlView.class);
	private BundleContext context  = bundle.getBundleContext();
	private ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
	private JavaEditorServices javaServices = context.getService(ref);
	private ArrayList<Node> nodes = new ArrayList<Node>();
	 
	public UmlView() {
		umlView = this;
	}

	@Override
	public void createContents(Composite umlArea, Map<String, Image> imageMap) {
		umlGraph = new Graph(umlArea, SWT.NONE);
		
		//UMLClassFigure f = new UMLClassFigure("COCO");
		//GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		
		
	}

	public static UmlView getInstance() {
		return umlView;
	}

	/**
	 * this is a recursive function, that draws the UML of all the classes of a selected package
	 * @param selection = element selected on the project browser
	 */
	public void paintUml(Collection<SourceElement> selection) {
		for(SourceElement e : selection){
			PackageElement p = null;
			//gets the next element
			//verifys if its a java package
			if(e.isPackage()){
				p = (PackageElement)e;
				//loop all java classes
				for(SourceElement classes : p.getChildren()){
					if(classes.isClass()){
						//this method is responsable for representing the javaclass on UML graph
						paintNode(classes);
					}else{
						//recursive, package inside the selected package
						if(classes.isPackage()){
							Collection<SourceElement> child;
							child = new ArrayList<SourceElement>();
							child.add(classes);
							paintUml(child);
							child=null;
						}	
					}
				}
			}
		
		umlGraph.applyLayout();
		connectUml();
		}
	}

	private void connectUml() {
		for(Node  node1 : nodes){
			for(Node  node2 : nodes){
				for(String ci: node1.getClassInstances()){
					if(ci.equals(node2.getName()) && node1!=node2){
						new GraphConnection(umlGraph, ZestStyles.CONNECTIONS_DIRECTED, node1.getNode(), node2.getNode());
					}
				}
				//ver
				sdwef
				if(node1.getClass().isInstance(node2.getClass()) && node1 != node2 && node1.isSuper()){
					System.out.println(node1.getName()+"-"+node2.getName());
					new GraphConnection(umlGraph, ZestStyles.NODES_FISHEYE, node1.getNode(), node2.getNode());
				}
				//if(node1.getNode().getText().contains(node2.getName()) && node1!=node2)
			}
		}
	}

	private synchronized void paintNode(SourceElement classes) {
		if(classes.getFile().getClass().isInterface()==true){
			System.out.println("LOOOL");
		}
		UmlVisitor visitor = new UmlVisitor();
		javaServices.parseFile(classes.getFile(), visitor);
		if(!visitor.getEnums().isEmpty()){
			paintEnum(visitor);
		}else{
			paintClass(classes, visitor);
		}
		
	}
	
	private void paintClass(SourceElement classes, UmlVisitor visitor) {
		String prefix = "Class ";
		if(visitor.isSuperClass()) {
			prefix = "Abstract "+prefix;
		}
		UMLClassFigure figure = new UMLClassFigure(prefix+classes.getName().replace(".java", ""));
		CGraphNode node = new CGraphNode(umlGraph, SWT.NONE, figure);
		
		//GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		//node.setText("Class "+classes.getName().replace(".java", "")+"\n");
		Node n = new Node(node, classes.getName().replace(".java", ""), classes);
		if(prefix.contains("Abstract")) n.setSuperClass(true);
		nodes.add(n);
		//node.setText(node.getText()+"---------------------------"+"\n");
		//for(ClassInstanceCreation ins : )
		for (int i = 0; i < visitor.getFields().size(); i++) {
			Object field = visitor.getFields().get(i).fragments().get(0);
			String fieldName = ((VariableDeclarationFragment) field).getName().toString();
			//node.setText(node.getText()+fieldName+" :"+visitor.getFields().get(i).getType()+"\n");
			n.addField(visitor.getFields().get(i).getType().toString());
			figure.addNameField(fieldName+" :"+visitor.getFields().get(i).getType());
			if(i==visitor.getFields().size()-1)
				figure.drawLine();
		}
		
		//figure.addNameField("______________________________________________");
		//node.setText(node.getText()+"___________________________"+"\n");
		for (int i = 0; i < visitor.getMethods().size(); i++) {
			if(!visitor.getMethods().get(i).isConstructor()){
				if(visitor.getMethods().get(i).getReturnType2()!=null){
					figure.addNameMethod(visitor.getMethods().get(i).getName()+" :"+visitor.getMethods().get(i).getReturnType2().toString());;
					//node.setText(node.getText()+"- "+visitor.getMethods().get(i).getName()+" :"+visitor.getMethods().get(i).getReturnType2().toString()+"\n");
				}else{
					figure.addNameMethod(visitor.getMethods().get(i).getName()+" : Void");
					//node.setText(node.getText()+"- "+visitor.getMethods().get(i).getName()+" : Void"+"\n");
				}
			}
		}
		n.setClassInstances(visitor.getClassInstances());
	}

	private void paintEnum(UmlVisitor visitor) {
		UMLClassFigure figure = new UMLClassFigure("Enum "+visitor.getEnums().get(0).getName());
		CGraphNode node = new CGraphNode(umlGraph, SWT.NONE, figure);
		figure.addNameMethod(node.getText()+visitor.getEnums().get(0).enumConstants());
		nodes.add(new Node(node, visitor.getEnums().get(0).getName().toString(), null));
		figure.drawLine();
		for (int i = 0; i < visitor.getMethods().size(); i++) {
			if(!visitor.getMethods().get(i).isConstructor()){
				if(visitor.getMethods().get(i).getReturnType2()!=null){
					figure.addNameMethod(visitor.getMethods().get(i).getName()+" :"+visitor.getMethods().get(i).getReturnType2().toString());;
					
				}else{
					figure.addNameMethod(visitor.getMethods().get(i).getName()+" : Void");
				}
			}
		}
		
	}

	public synchronized void clearGraph() {
		nodes.clear();
		List<GraphConnection> connections = new ArrayList<GraphConnection>(umlGraph.getConnections());
		for(GraphConnection c : connections){
		  c.dispose();
		}
		List<GraphNode> nodes = new ArrayList<GraphNode>(umlGraph.getNodes());
		for(GraphNode n : nodes){
		  n.dispose();
		}
	}
}
