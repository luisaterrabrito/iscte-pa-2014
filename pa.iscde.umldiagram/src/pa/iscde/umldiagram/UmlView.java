package pa.iscde.umldiagram;


import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.lang.model.element.Element;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
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
import pt.iscte.pidesco.projectbrowser.model.PackageElement.Visitor;
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
	private JavaEditorServices javaServices;
	private ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
	private ProjectBrowserServices browserServices;
	
	
	public UmlView() {
		umlView = this;
		javaServices = context.getService(ref);
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

	private void paintNode(SourceElement classes) {
		
		UmlVisitor visitor = UmlVisitor.getInstance();
		javaServices.parseFile(classes.getFile(), visitor);
		GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		node.setText(classes.getName().replace(".java", ""));
		for (int i = 0; i < visitor.getMethods().size(); i++) {
			//node.
		}
		
		
	}



	public void setBrowserServices(ProjectBrowserServices browserServices) {
		this.browserServices=browserServices;
		
	}



	public void clearGraph() {
		while(umlGraph.getConnections().iterator().hasNext()){
			umlGraph.getConnections().remove(umlGraph.getConnections().iterator().next()) ; 
		}
		while(umlGraph.getNodes().iterator().hasNext()){
			umlGraph.getNodes().remove(umlGraph.getNodes().iterator().next()) ; 
			
		}
		
		umlGraph.redraw();
		umlGraph.applyLayout();
		umlGraph.redraw();
		umlGraph.getParent().update();
		umlGraph.getParent().redraw();
		
	}

}
