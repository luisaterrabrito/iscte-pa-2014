package pa.iscde.packagediagram.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
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

	//pesquisa as classes/nós
	private void searchClass(PackageElement root) {
		for (SourceElement e : root.getChildren()) {
			if (e.isPackage()) {
				searchClass((PackageElement) e);
			}
			if (e.isClass()) {
				PackageDiagramASTVisitor packageDiagramAstVisitor = new PackageDiagramASTVisitor();
				javaServices.parseFile(e.getFile(), packageDiagramAstVisitor);

				
				PackageDeclaration packageDeclaration = packageDiagramAstVisitor.getPackageDeclaration();
				String name = packageDeclaration == null ? "packageDefault" : packageDeclaration.getName().toString();
			

				//vai buscar o packageNode - nó
				NodeModel packageNode = new NodeModel(name);
				if (!nodes.contains(packageNode)) {
					nodes.add(packageNode);
				}
				else
					packageNode = nodes.get(nodes.indexOf(packageNode));

				//buscar o nome do IMPORT e filtra o nome
				for (ImportDeclaration x : packageDiagramAstVisitor.getImportList()) {

					String packageImport = x.getName().toString();
					

					String[] listString = packageImport.split("\\.");
					
					int i;

					//filtra até tirar o nome da classe, LETRA MAIÚSCULA
					for(i = listString.length-1; i>=0; i--) {
						String str =  listString[i];

						if (!Character.isUpperCase(str.charAt(0))){
							break;
						}
					}
					
					// ESCREVE A PALAVRA FILTRADA NO PACKAGEIMPORT
					packageImport = "";
					for (int j=0; j<=i; j++) {
						if(packageImport.length()>0)
							packageImport += ".";
						packageImport += listString[j];
					}
				
					
					// ignorar import a si próprios
					if(packageNode.getName().compareTo(packageImport)==0)
						continue;
					
					// cria nó com o nome filtrado e correto
					NodeModel importNode = new NodeModel(packageImport);
					
					if (!nodes.contains(importNode)) {
						nodes.add(importNode);
					}
					else
						importNode = nodes.get(nodes.indexOf(importNode));
					
					// cria ligação
					new ConnectionModel("import", packageNode, importNode);
				}

			}

		}

	}
	
	 public List<NodeModel> getNodes() {
		    return nodes;
		  }
	
}
