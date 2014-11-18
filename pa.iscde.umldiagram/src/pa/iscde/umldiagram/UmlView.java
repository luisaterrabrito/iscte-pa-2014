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
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

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

	public void paintUml(SourceElement currentPackage) {
		PackageElement p = null;
		if(currentPackage.isPackage()){
			p = (PackageElement)currentPackage;
			for(SourceElement classes : p.getChildren()){
				if(classes.isClass()){
					paintNode(classes);
				}else{
					if(classes.isPackage()){
						paintUml(classes);
					}
				}
			}
			umlGraph.applyLayout();
		}
	}

	private void paintNode(SourceElement classes) {
		ASTVisitor visitor = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
				
				return false;
			}
		};
		javaServices.parseFile(classes.getFile(), visitor);
		GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		node.setText(classes.getName().replace(".java", ""));
		
	}



	public void setBrowserServices(ProjectBrowserServices browserServices) {
		this.browserServices=browserServices;
		
	}



	public void clearGraph() {
		Object[] objects = umlGraph.getNodes().toArray() ; 

		
				
		
	}

}
