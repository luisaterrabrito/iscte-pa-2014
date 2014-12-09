package pa.iscde.packagediagram.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.packagediagram.internal.PackageDiagramASTVisitor;
import pa.iscde.packagediagram.internal.PackageDiagramView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class NodeModelContent {

	private List <NodeModel> nodes;
	private List <ConnectionModel> connections;
	private JavaEditorServices javaServices;
	
	public NodeModelContent(PackageElement root){
		Bundle bundle = FrameworkUtil.getBundle(PackageDiagramView.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context
				.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);

	    nodes = new ArrayList<NodeModel>();
	    connections = new ArrayList<ConnectionModel>();
	    
	    searchClass(root);
		
	}
	
	/**
	 * private void searchPackage(PackageElement root, GraphNode parentNode) {
	 * for(SourceElement e : root.getChildren()) if(e.isPackage()) { GraphNode
	 * sonNode = new GraphNode(graph, SWT.NONE, e.getName());
	 * 
	 * new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, parentNode,
	 * sonNode); searchPackage((PackageElement) e, sonNode); } }
	 **/

	private void searchClass(PackageElement root) {
		for (SourceElement e : root.getChildren()) {
			if (e.isPackage()) {
				searchClass((PackageElement) e);
			}
			if (e.isClass()) {
				PackageDiagramASTVisitor packageDiagramAstVisitor = new PackageDiagramASTVisitor();
				javaServices.parseFile(e.getFile(), packageDiagramAstVisitor);

				String packageDeclaration = packageDiagramAstVisitor
						.getPackageDeclaration().getName().toString();

				NodeModel packageNode = new NodeModel(packageDeclaration,packageDeclaration);
				if (!nodes.contains(packageNode)) {
					nodes.add(packageNode);
				}
				else
					packageNode = nodes.get(nodes.indexOf(packageNode));


				for (ImportDeclaration x : packageDiagramAstVisitor
						.getImportList()) {

					String packageImport = x.getName().toString();
					

					String[] listString = packageImport.split("\\.");
					
					int i;

					for(i = listString.length-1; i>=0; i--) {
						String str =  listString[i];

						if (!Character.isUpperCase(str.charAt(0))){
							break;
						}
					}
					
					
					packageImport = "";
					for (int j=0; j<=i; j++) {
						if(packageImport.length()>0)
							packageImport += ".";
						packageImport += listString[j];
					}
					

					
					
					
					NodeModel importNode = new NodeModel(packageImport,packageImport);
					
					if (!nodes.contains(importNode)) {
						nodes.add(importNode);
					}
					else
						importNode = nodes.get(nodes.indexOf(importNode));
					
					new ConnectionModel("import", "import", packageNode, importNode);
				}

			}

		}

	}
	
	 public List<NodeModel> getNodes() {
		    return nodes;
		  }
	
}
