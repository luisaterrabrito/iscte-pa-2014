package pa.iscde.umldiagram;


	

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
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

import pa.iscde.umldiagram.UmlTheme.ClassType;
import pa.iscde.umldiagram.ClickOption;
import pa.iscde.umldiagram.utils.UmlVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * this class handles all the actions of umldiagram, this means:
 * its responsable to find and run all the umldiagram extension points;
 * its responsable to implement the main funcionality: paint the uml diagram and draw the connections
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
	private HashMap<String, ChangeTheme> themes = new HashMap<String, ChangeTheme>();
	private MouseListener opListener;
	private ArrayList<ClickOption> actions = new ArrayList<ClickOption>();

	 
	public UmlView() {
		umlView = this;
	}
	
	/*
	 * class auxiliar para implementar os pontos de extensão
	 */
	public static class ChangeTheme{
		private UmlTheme cTheme;
		
		public ChangeTheme(UmlTheme cTheme){
			this.cTheme=cTheme;
		}
		
		public Color getColor(String className, ClassType type){
			return cTheme.getColor(className, type);
		}

		public UmlTheme getTheme() {
			return cTheme;
		}
	}

	@Override
	public void createContents(Composite umlArea, Map<String, Image> imageMap) {
		umlGraph = new Graph(umlArea, SWT.NONE);
		setMouseListener();
		loadColorThemeExtensions();
		loadClickOptionExtensions();
		Menu menu = new Menu(umlGraph);
		Iterator it = themes.entrySet().iterator();
		MenuItem item=null;
	    while (it.hasNext()) {
	        Map.Entry name = (Map.Entry)it.next();
	        item = new MenuItem(menu, SWT.PUSH);
			item.setText(name.getKey().toString());
	        item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					MenuItem aux = (MenuItem)e.getSource();
					ChangeTheme cTheme = themes.get(aux.getText());
						if(cTheme!=null){
							for (Node n : nodes) {
								Color color = cTheme.getColor(cTheme.getTheme().getClassName(), cTheme.getTheme().getClassType());
									if(color!=null){
										if(cTheme.getTheme().getClassType() == ClassType.ALL){
											n.getNode().setBackgroundColor(color);
										}else{
											if(cTheme.getTheme().getClassType() != ClassType.NONE){
												if(!cTheme.getTheme().getClassName().isEmpty()){
													if(n.getName().equals(cTheme.getTheme().getClassName()) && n.getType() == cTheme.getTheme().getClassType()){
														n.getNode().setBackgroundColor(color);
													}
												}else{
													if(n.getType() == cTheme.getTheme().getClassType()){
														n.getNode().setBackgroundColor(color);
													}
												}
											}else{
												if(!cTheme.getTheme().getClassName().isEmpty()){
													if(cTheme.getTheme().getClassName().equals(n.getName())){
														n.getNode().setBackgroundColor(color);
													}
												}
											}
										}
									
								}
							}
					}
				}
			});
	    }
		
		umlGraph.setMenu(menu);
	}

	private void setMouseListener() {
		umlGraph.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try{
					Graph g = (Graph)e.getSource();
					for (int i = 0; i < g.getNodes().size(); i++) {
						CGraphNode n = (CGraphNode)g.getNodes().get(i);
						Point p = n.getLocation();
						Point p2 = new Point(p.x+n.getSize().width, p.y + n.getSize().height);
						if(e.x >= p.x && e.x <= p2.x && e.y >= p.y && e.y <= p2.y){
							
							getActions(n);
						}
							
					}
				//CGraphNode f = (CGraphNode)e.getSource();
				
				}catch(Exception ex){
					System.out.println("Erro ao extender a interface clickOption");
				}
				
			}

			
		});
			
			
		
	}
	
	
	private void getActions(CGraphNode n) {
		for (int i = 0; i < actions.size(); i++) {
			actions.get(i).getAction(n);
		}
	}

	private void loadClickOptionExtensions() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint("pa.iscde.umldiagram.clickoption").getExtensions()) {
			String name = ext.getLabel();
			if(ext.getConfigurationElements().length>0){
				IConfigurationElement element = ext.getConfigurationElements()[0];
				try {

					ClickOption op=(ClickOption)element.createExecutableExtension("class");
					actions .add(op);
					
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static UmlView getInstance() {
		return umlView;
	}

	private void loadColorThemeExtensions() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint("pa.iscde.umldiagram.colortheme").getExtensions()) {
			String name = ext.getLabel();
			if(ext.getConfigurationElements().length>0){
				IConfigurationElement element = ext.getConfigurationElements()[0];
				try {
					UmlTheme t = (UmlTheme) element.createExecutableExtension("class");
					themes.put(name, new ChangeTheme(t));
				} catch (CoreException e) {
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

		if(prefix.equals("")){
			node.setText("<class>" + cName);
		}else{
			node.setText(prefix + cName);
		}
		
		figure.setNode(node);
		Node n = new Node(node, cName, classes, figure);
		
		if(prefix.contains("<enum>")){
			n.setType(ClassType.ENUM);
		}else{
			if(prefix.contains("<interface")){
				n.setType(ClassType.INTERFACE);
			}else{
				n.setType(ClassType.CLASS);
			}
		}
		
		
		nodes.add(n);
		for (int i = 0; i < visitor.getFields().size(); i++) {
			Object field = visitor.getFields().get(i).fragments().get(0);
			String fieldName = ((VariableDeclarationFragment) field).getName().toString();
			n.addField(visitor.getFields().get(i).getType().toString());
			figure.addNameField(fieldName+" :"+visitor.getFields().get(i).getType());
			if(i==visitor.getFields().size()-1)
				figure.drawLine();
		}
		
		for (int i = 0; i < visitor.getMethods().size(); i++) {
			if(!visitor.getMethods().get(i).isConstructor()){
				if(visitor.getMethods().get(i).getReturnType2()!=null){
					figure.addNameMethod(visitor.getMethods().get(i).getName()+" :"+visitor.getMethods().get(i).getReturnType2().toString());;
				}else{
					figure.addNameMethod(visitor.getMethods().get(i).getName()+" : Void");
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
