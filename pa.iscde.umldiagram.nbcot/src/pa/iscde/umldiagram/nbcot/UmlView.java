package pa.iscde.umldiagram.nbcot;


	

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.CGraphNode;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.umldiagram.nbcot.UmlTheme.ClassType;
import pa.iscde.umldiagram.nbcot.utils.UmlVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

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
	private String properties = "UmlView umlView (singleton), Graph umlGraph, Bundle bundle, ServiceReference<JavaEditorServices> ref, JavaEditorServices javaServices, ArrayList<Node> nodes";

	 
	public Graph getUmlGraph() {
		return umlGraph;
	}

	public UmlView() {
		umlView = this;
	}
	
	/*
	 * class auxiliar para implementar os pontos de extens�o
	 */
	public static class ChangeTheme{
		private UmlTheme cTheme;
		
		public ChangeTheme(UmlTheme cTheme){
			this.cTheme=cTheme;
		}
		
		public Color getColor(String className){
			return cTheme.getColor(className);
		}

		public UmlTheme getTheme() {
			return cTheme;
		}
	}

	@Override
	public void createContents(Composite umlArea, Map<String, Image> imageMap) {
		umlGraph = new Graph(umlArea, SWT.NONE);
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
				if(node1.getSuperC()!=null){
					if(node1.getSuperC().equals(node2.getName())){
						GraphConnection c = new GraphConnection(umlGraph, ZestStyles.CONNECTIONS_DIRECTED, node1.getNode(), node2.getNode());
						c.setText("extends");
					}
				}
				if(node1.getImplementClasses()!=null){
					for(String implementedClass : node1.getImplementClasses()){
						if(implementedClass.equals(node2.getName().toString())){
							GraphConnection c = new GraphConnection(umlGraph, ZestStyles.CONNECTIONS_DIRECTED, node1.getNode(), node2.getNode());
							c.setText("implements");
						}
					}
				}
			}
		}
	}

	private synchronized void paintNode(SourceElement classes) {
		UmlVisitor visitor = new UmlVisitor();
		javaServices.parseFile(classes.getFile(), visitor);
		paintClass(classes, visitor);
		//setListener();
		
	}
	
	private void paintClass(SourceElement classes, UmlVisitor visitor) {
		String prefix = "";
		String cName = classes.getName().replace(".java", "");

		//System.out.println(":"+cName);

		if(visitor.isInterface()) {
			prefix = "<interface> ";
		}
		for (EnumDeclaration e: visitor.getEnums()) {
			if(e.getName().toString().equals(cName)){
				prefix = "<enum> ";
			}
		}
		UMLClassFigure figure = new UMLClassFigure(prefix+cName);

		
		CGraphNode node = new CGraphNode(umlGraph, SWT.NONE, figure);

		if(prefix.equals("")){
			node.setText("<class>" + cName);
		}else{
			node.setText(prefix + cName);
		}
		
		figure.setNode(node);
		//GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		//node.setText("Class "+classes.getName().replace(".java", "")+"\n");
		Node n = new Node(node, cName, classes, figure);
		//if(themes.containsValue(null)){
		if(prefix.contains("<enum>")){
			n.setType(ClassType.ENUM);
		}else{
			if(prefix.contains("<interface")){
				n.setType(ClassType.INTERFACE);
			}else{
				n.setType(ClassType.CLASS);
			}
		}
		
			//figure.setBackgroundColor(themes.get(null).getColor(cName.getClass()));
		//}
		
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
		
		for (EnumDeclaration e: visitor.getEnums()) {
			if(!e.getName().equals(cName)){
				figure.addNameMethod("enum "+e.getName()+" : "+e.enumConstants());
			}
		}
		
		n.setClassInstances(visitor.getClassInstances());
		n.setSuperClass(visitor.getSuperClass());
		n.setImplementClasses(visitor.getImplementClasses());

		

	}


	

	public synchronized void clearGraph() {
		nodes.clear();
		if(umlGraph != null){
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

	public String getProperties() {
		return properties;
	}

}
