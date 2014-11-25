package pa.iscde.umldiagram;


import java.awt.Color;
import java.awt.Font;
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
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
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
	
	public UmlView() {
		umlView = this;
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
		
		}
	}

	private synchronized void paintNode(SourceElement classes) {
		
		UmlVisitor visitor = new UmlVisitor();
		javaServices.parseFile(classes.getFile(), visitor);
		if(!visitor.getEnums().isEmpty() && visitor.getMethods().isEmpty()){
			paintEnum(visitor);
		}else{
			paintClass(classes, visitor);
		}
	}
	
	private void paintClass(SourceElement classes, UmlVisitor visitor) {
		GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		node.setText("Class "+classes.getName().replace(".java", "")+"\n");
		node.setText(node.getText()+"---------------------------"+"\n");
		for (int i = 0; i < visitor.getFields().size(); i++) {
			Object field = visitor.getFields().get(i).fragments().get(0);
			String fieldName = ((VariableDeclarationFragment) field).getName().toString();
			node.setText(node.getText()+fieldName+" :"+visitor.getFields().get(i).getType()+"\n");
		}
		node.setText(node.getText()+"___________________________"+"\n");
		for (int i = 0; i < visitor.getMethods().size(); i++) {
			if(!visitor.getMethods().get(i).isConstructor()){
				if(visitor.getMethods().get(i).getReturnType2()!=null){
					node.setText(node.getText()+"- "+visitor.getMethods().get(i).getName()+" :"+visitor.getMethods().get(i).getReturnType2().toString()+"\n");
				}else{
					node.setText(node.getText()+"- "+visitor.getMethods().get(i).getName()+" : Void"+"\n");
				}
			}
		}
	}

	private void paintEnum(UmlVisitor visitor) {
		GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		node.setText("Enum "+visitor.getEnums().get(0).getName()+"\n");
		node.setText(node.getText()+"---------------------------"+"\n");
		node.setText(node.getText()+visitor.getEnums().get(0).enumConstants());
	}

	public synchronized void clearGraph() {
		List<GraphNode> nodes = new ArrayList<GraphNode>(umlGraph.getNodes());
		for(GraphNode n : nodes){
		  n.dispose();
		}
	}

}
