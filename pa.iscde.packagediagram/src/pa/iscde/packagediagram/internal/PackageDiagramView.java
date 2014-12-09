package pa.iscde.packagediagram.internal;



import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;
import pa.iscde.packagediagram.extensibility.PackageDiagramFilterExtension;
import pa.iscde.packagediagram.model.NodeModelContent;
import pa.iscde.packagediagram.provider.MyLabelProvider;
import pa.iscde.packagediagram.provider.ContentProvider;
import pt.iscte.pidesco.extensibility.PidescoView;



import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class PackageDiagramView implements PidescoView {

	//public static final String ID = "de.vogella.zest.first.view";
	private GraphViewer graph;

//	private Map<String, GraphNode> example = new HashMap<String, GraphNode>();

	private static final String EXT_POINT_COLOR = "pa.iscde.packagediagram.colorSelector";

	private static PackageDiagramView instance;

	public static PackageDiagramView getInstance() {
		return instance;
	}

//	private JavaEditorServices javaServices;

	private ProjectBrowserServices browserServices;

	public PackageDiagramView() {

		Bundle bundle = FrameworkUtil.getBundle(PackageDiagramView.class);
		BundleContext context = bundle.getBundleContext();


		ServiceReference<ProjectBrowserServices> ref2 = context
				.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref2);

		loadChangeColor();
	
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;

		
		graph = new GraphViewer(viewArea, SWT.NONE);

		PackageElement root = browserServices.getRootPackage();
		
		/**
		 * for(SourceElement e : root.getChildren()) if(e.isPackage()) {
		 * GraphNode node = new GraphNode(graph, SWT.NONE, e.getName());
		 * searchPackage((PackageElement) e,node); }
		 **/


		graph.setLabelProvider(new MyLabelProvider(colorsMap));
		graph.setContentProvider(new ContentProvider());

		NodeModelContent model = new NodeModelContent(root);
		graph.setInput(model.getNodes());


		LayoutAlgorithm layout = setLayout();
		graph.setLayoutAlgorithm(layout, true);
		graph.applyLayout();
		
		
	}


	private LayoutAlgorithm setLayout() {
		LayoutAlgorithm layout;
		layout = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);

		return layout;

	}

	public void refresh() {

PackageElement root = browserServices.getRootPackage();
		
		/**
		 * for(SourceElement e : root.getChildren()) if(e.isPackage()) {
		 * GraphNode node = new GraphNode(graph, SWT.NONE, e.getName());
		 * searchPackage((PackageElement) e,node); }
		 **/


//		graph.setLabelProvider(new MyLabelProvider(colorsMap));
//		graph.setContentProvider(new ContentProvider());
//
//		NodeModelContent model = new NodeModelContent(root);
//		graph.setInput(model.getNodes());
//
//
//		LayoutAlgorithm layout = setLayout();
//		graph.setLayoutAlgorithm(layout, true);
//		graph.applyLayout();
		
		
		

		System.out.println("refrescante");
	}
	

	private Map<String, ChangeFilter> filterMap = new HashMap<String, ChangeFilter>();
	
	// Carrega todas as extensões
	public void loadChangeFilter() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_COLOR).getExtensions()) {
			
			PackageDiagramFilterExtension pdfe = null;
			try {
				pdfe = (PackageDiagramFilterExtension) ext.getConfigurationElements()[0].createExecutableExtension("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(pdfe != null) {
				filterMap.put(ext.getUniqueIdentifier(), new ChangeFilter(pdfe));
			}
		}
	}
	
	private Map<String, ChangeColor> colorsMap = new HashMap<String, ChangeColor>();
	
	// Carrega todas as extensões
	public void loadChangeColor() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_COLOR).getExtensions()) {
			
			PackageDiagramColorExtension pcce = null;
			try {
				pcce = (PackageDiagramColorExtension) ext.getConfigurationElements()[0].createExecutableExtension("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(pcce != null) {
				colorsMap.put(ext.getUniqueIdentifier(), new ChangeColor(pcce));
			}
		}
	}
	
	
	
	// a classe que tem o método implementado nas extensões para usar mais adiante
	public static class ChangeColor{

		
		private PackageDiagramColorExtension tempPcce;
		
		public ChangeColor(PackageDiagramColorExtension pcce) {
			tempPcce = pcce;		
		}
		
		public Color getForeground(String packageName) {
			return tempPcce.changeColorLetter(packageName);
		}
		
		public Color getBackground(String packageName) {
			return tempPcce.changeColorBackground(packageName);
		}
	
	};
	
	
	public static class ChangeFilter {
		
		private PackageDiagramFilterExtension tempPdfe;
		
		public ChangeFilter (PackageDiagramFilterExtension pdfe){
			tempPdfe = pdfe;
		}
		
		// ficamos aqui
		
		
	};
	
	
	
	
	

}
