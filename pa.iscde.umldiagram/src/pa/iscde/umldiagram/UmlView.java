package pa.iscde.umldiagram;


	
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
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
	private HashMap<String, UmlTheme> themes = new HashMap<String, UmlTheme>();
	
	 
	public UmlView() {
		umlView = this;
	}

	@Override
	public void createContents(Composite umlArea, Map<String, Image> imageMap) {
		umlGraph = new Graph(umlArea, SWT.NONE);
		loadColorThemeExtensions();
		//UMLClassFigure f = new UMLClassFigure("COCO");
		//GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		
		Menu menu = new Menu(umlGraph);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("Change Theme");
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("menu");
			}
		});
		umlGraph.setMenu(menu);
	}

	public static UmlView getInstance() {
		return umlView;
	}

	private void loadColorThemeExtensions() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint("pa.iscde.umldiagram.colortheme").getExtensions()) {
			String name = ext.getLabel();
			System.out.println(name);
			if(ext.getConfigurationElements().length>0){
				IConfigurationElement element = ext.getConfigurationElements()[0];
				try {
					UmlTheme t = (UmlTheme) element.createExecutableExtension("class");
					themes.put(name, t);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
					if(node1 != node2 && node1.getSuperC().equals(node2.getName())){
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
		
		
	}
	
	private void paintClass(SourceElement classes, UmlVisitor visitor) {
		String prefix = "";
		String cName = classes.getName().replace(".java", "");
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
		
		
		//GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		//node.setText("Class "+classes.getName().replace(".java", "")+"\n");
		Node n = new Node(node, cName, classes);
		//if(themes.containsValue(null)){
		
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

//	private void paintEnum(UmlVisitor visitor) {
//		UMLClassFigure figure = new UMLClassFigure("Enum "+visitor.getEnums().get(0).getName());
//		CGraphNode node = new CGraphNode(umlGraph, SWT.NONE, figure);
//		figure.addNameMethod(node.getText()+visitor.getEnums().get(0).enumConstants());
//		nodes.add(new Node(node, visitor.getEnums().get(0).getName().toString(), null));
//		figure.drawLine();
//		for (int i = 0; i < visitor.getMethods().size(); i++) {
//			if(!visitor.getMethods().get(i).isConstructor()){
//				if(visitor.getMethods().get(i).getReturnType2()!=null){
//					figure.addNameMethod(visitor.getMethods().get(i).getName()+" :"+visitor.getMethods().get(i).getReturnType2().toString());;
//					
//				}else{
//					figure.addNameMethod(visitor.getMethods().get(i).getName()+" : Void");
//				}
//			}
//		}
//		
//	}

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
