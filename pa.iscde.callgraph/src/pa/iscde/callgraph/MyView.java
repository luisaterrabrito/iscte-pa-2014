package pa.iscde.callgraph;

import java.io.File;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.callgraph.extensibility.CustomLayout;
import pa.iscde.callgraph.extensibility.ExportButton;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement.Visitor;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


/**
 * 
 * This class is responsible for creating the CallGraph view plugin for ISCDE IDE.
 * 
 * @author João Alves, Pedro Neves
 *
 */
public class MyView implements PidescoView {

	private static final String LAYOUT_EXT_POINT_ID = "pa.iscde.callgraph.customLayoutProvider";
	private static final String EXPORT_EXT_POINT_ID = "pa.iscde.callgraph.exportAsProvider";
	private Composite viewArea;
	private Combo cmbLayout;
	private JavaEditorServices javaServices;
	private ProjectBrowserServices projectBrowser;
	private HashMap<String, LayoutAlgorithm> layouts;
	private HashMap<String, ExportButton> exportButtons;
	private Graph graph;
	private static MyView instance;
	private Combo cmbPluginsList;
	public Image callGraphIcon;
	private MethodDeclaration currentMethod;
	private ArrayList<MethodDeclaration> aboveMethods;
	private ArrayList<MethodInvocation> belowMethods;
	
	/**
	 * 
	 * @return CallGraph view instance
	 */
	public static MyView getInstance() {
		return instance;
	}

	/**
	 * 
	 * MyView class constructor
	 */
	public MyView() {
		Bundle bundle = FrameworkUtil.getBundle(MyView.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> javaEditorServicesRef = context
				.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(javaEditorServicesRef);
		ServiceReference<ProjectBrowserServices> projectBrowserServicesRef = context.getServiceReference(ProjectBrowserServices.class);
		projectBrowser = context.getService(projectBrowserServicesRef);
	}
	
	/**
	 * 
	 * Searches for custom layouts and incorporates them in the GUI
	 */
	public void loadExportButtons(Shell shell){
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for (IExtension ext : reg.getExtensionPoint(
				EXPORT_EXT_POINT_ID).getExtensions()) {
			try {
				IConfigurationElement comp = ext
						.getConfigurationElements()[0];
				ExportButton exportButton = (ExportButton) comp
						.createExecutableExtension("class");
				exportButtons.put(comp.getAttribute("name"),
						exportButton);
			} catch (CoreException e1) {
				MessageBox messageBox = new MessageBox(shell,
						SWT.ICON_ERROR | SWT.OK);
				messageBox
						.setText("Error loading plugins for CallGraph!");
				messageBox.open();
			}
		}
	}
	
	/**
	 * 
	 * Searches for export buttons and incorporates them in the GUI
	 */
	public void loadCustomLayouts(){
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for (IExtension ext : reg.getExtensionPoint(LAYOUT_EXT_POINT_ID)
				.getExtensions()) {
			try {
				IConfigurationElement comp = ext.getConfigurationElements()[0];
				CustomLayout customlayout = (CustomLayout) comp
						.createExecutableExtension("class");
				LayoutAlgorithm layout = customlayout.getCustomLayout();
				layouts.put(comp.getAttribute("name"), layout);
			} catch (CoreException e1) {
				MessageBox messageBox = new MessageBox(viewArea.getShell(),
						SWT.ICON_ERROR | SWT.OK);
				messageBox.setText("Error loading custom layouts for CallGraph!");
				messageBox.open();
			}
		}
	}

	/**
	 * 
	 * Creates GUI
	 * 
	 * @param viewArea composite where the contents of the view should be added (by default, it is configured with the FillLayout)
	 * @param imageMap map indexing the images contained in the "images" folder of the plugin 
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		System.out.println(callGraphIcon);
		this.viewArea = viewArea;
		layouts = new HashMap<String, LayoutAlgorithm>();
		exportButtons = new HashMap<String, ExportButton>();
		loadCustomLayouts();
		layouts.put("Tree Layout Algorithm", new TreeLayoutAlgorithm());
		layouts.put("Radial Layout Algorithm", new RadialLayoutAlgorithm());
		layouts.put("Horizontal Tree Layout Algorithm",
				new HorizontalTreeLayoutAlgorithm());
		layouts.put("Grid Layout Algorithm", new GridLayoutAlgorithm());
		viewArea.setLayout(new GridLayout(3, false));
		GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		data.horizontalSpan = 3;
		Label lblSelect = new Label(viewArea, SWT.NONE);
		lblSelect.setText("Select call graph layout:");
		data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		lblSelect.setLayoutData(data);
		cmbLayout = new Combo(viewArea, SWT.BORDER | SWT.READ_ONLY);
		String[] cmbStrings = new String[layouts.keySet().size()];
		Iterator<String> keySetIterator = layouts.keySet().iterator();
		int x = 0;
		while (keySetIterator.hasNext()) {
			cmbStrings[x] = keySetIterator.next();
			x++;
		}
		cmbLayout.setItems(cmbStrings);
		data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		cmbLayout.setLayoutData(data);
		cmbLayout.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}

			public void widgetSelected(SelectionEvent arg0) {
				graph.setLayoutAlgorithm(layouts.get("" + cmbLayout.getText()),
						true);
			}
		});

		Button btnExport = new Button(viewArea, SWT.PUSH);
		btnExport.setText("Export as...");
		data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		btnExport.setLayoutData(data);
		btnExport.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					final Display display = Display.getDefault();
					final Shell shell = new Shell(display);
					shell.setMinimumSize(200, 50);
					shell.setText("Export as...");
					loadExportButtons(shell);
					cmbPluginsList = new Combo(shell, SWT.BORDER
							| SWT.READ_ONLY);
					cmbPluginsList.setText("Select plugin");
					String[] cmbStrings = new String[exportButtons.keySet()
							.size()];
					Iterator<String> keySetIterator = exportButtons.keySet()
							.iterator();
					int x = 0;
					while (keySetIterator.hasNext()) {
						cmbStrings[x] = keySetIterator.next();
						x++;
					}
					Button btnChoosePlugin = new Button(shell, SWT.PUSH);
					cmbPluginsList.setItems(cmbStrings);
					if (cmbStrings.length != 0) {
						cmbPluginsList.select(0);
					} else {
						MessageBox messageBox = new MessageBox(shell,
								SWT.ICON_ERROR | SWT.OK);
						messageBox
								.setText("Export as...");
						messageBox.setMessage("No plugin was found!");
						messageBox.open();
						cmbPluginsList.setEnabled(false);
						btnChoosePlugin.setEnabled(false);
						shell.dispose();
					}
					GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true,
							false);
					shell.setLayout(new GridLayout(3, false));
					data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
					cmbPluginsList.setLayoutData(data);
					btnChoosePlugin.setText("Ok");
					data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
					btnChoosePlugin.setLayoutData(data);
					btnChoosePlugin.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event arg0) {
							exportButtons.get("" + cmbPluginsList.getText()).export
							(currentMethod, aboveMethods, belowMethods, display);
							shell.dispose();
						}
					});

					shell.pack();
					shell.open();
					while (!shell.isDisposed()) {
						if (!display.readAndDispatch())
							display.sleep();
					}
					break;
				}
			}
		});
		graph = new Graph(viewArea, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 3;
		graph.setLayoutData(data);
	}

	/**
	 * Retrieves every class in Visitor() and stores them in a HashMap,
	 * along with a reference to the class, its methodsDeclarations and methodsInvocations.
	 * 
	 * Creates the Call Graph when user changes the selected text.
	 * 
	 * @param file the file (java class) in which the selected method is stored.
	 */
	public void newFile(File file) {

		// Hashmap que guarda os métodos declarados e a invocação de métodos da classe seleccionada
		final HashMap<MethodDeclaration, ArrayList<MethodInvocation>> invocationsForMethods =
				new HashMap<MethodDeclaration, ArrayList<MethodInvocation>>();

		// Hashmap que guarda todos os métodos (delcarados e invocados) de acordo com a classe dentro
		// do pacote
		final HashMap<String, HashMap<MethodDeclaration, ArrayList<MethodInvocation>>> classMethods =
				new HashMap<String, HashMap<MethodDeclaration,ArrayList<MethodInvocation>>>();

		
		// Visitor() que percorre todas as classes do package e guarda 
		// o nome da mesma como key no hashmap
		projectBrowser.getRootPackage().traverse(new Visitor() {

			private String className;

			@Override
			public boolean visitPackage(PackageElement packageElement) {
				return true;
			}

			@Override
			public void visitClass(ClassElement classElement) {
				className = classElement.getName();
				classMethods.put(className, new HashMap<MethodDeclaration, ArrayList<MethodInvocation>>());
				
				// ASTVisitor() que percorre todos os métodos da classe percorrida pelo Visitor()
				// anterior e guarda os métodos dentro de um novo hashmap onde a key é o método
				// declarado e o value() é uma lista de métodos invocados (se existir)
				ASTVisitor vis = new ASTVisitor() {

					private MethodDeclaration activeMethod;

					@Override
					public boolean visit(MethodDeclaration node) {
						activeMethod = node;
						classMethods.get(className).put(activeMethod, new ArrayList<MethodInvocation>());
						return super.visit(node);
					}

					@Override
					public boolean visit(MethodInvocation node) {
						classMethods.get(className).get(activeMethod).add(node);
						return super.visit(node);
					}

				};
				javaServices.parseFile(classElement.getFile(), vis);
			}

		});

		clearGraph(graph);
		
		// ASTVisitor() que percorre todos os métodos (declarados ou invocados) da classe
		// aberta no editor
		ASTVisitor visitor = new ASTVisitor() {

			private MethodDeclaration activeMethod;

			@Override
			public boolean visit(MethodDeclaration node) {
				int posStart = node.getStartPosition();
				int posFinal = node.getLength() + posStart;
				int point = javaServices.getCursorPosition();

				
				// Condição que indica se o texto (ou cursor) está seleccionado nalgum método
				if(point < posFinal && point > posStart) {
					currentMethod = node;
					//					activeMethod = node;
					//					invocationsForMethods.put(activeMethod, new ArrayList<MethodInvocation>());
				}
				activeMethod = node;
				invocationsForMethods.put(activeMethod, new ArrayList<MethodInvocation>());
				return super.visit(node);
			}

			@Override
			public boolean visit(MethodInvocation node) {
				invocationsForMethods.get(activeMethod).add(node);
				return super.visit(node);
			}
		};

		javaServices.parseFile(javaServices.getOpenedFile(), visitor);

		// Itera no hashmap
		Iterator<MethodDeclaration> keySetIterator = invocationsForMethods.keySet().iterator();
		while(keySetIterator.hasNext()) {

			MethodDeclaration key = keySetIterator.next();

			// Se o texto (ou cursor) está seleccionado nalgum método, é criado na View, um GraphNode
			// como o nó central (seleccionado)
			
			// Azul - Método seleccionado
			// Vermelho - Métodos de outras classes que chamam o método seleccionado
			// Verde - Métodos que são invocados dentro do método seleccionado
			
			if(key == currentMethod) {

				aboveMethods = new ArrayList<MethodDeclaration>();
				belowMethods = new ArrayList<MethodInvocation>();
				
				// Métodos abaixo do nó central (seleccionado)
				GraphNode nodeKey = new GraphNode(graph, SWT.NONE, "" + key.getName());
				nodeKey.setBackgroundColor(viewArea.getDisplay().getSystemColor(SWT.COLOR_BLUE));
				
				// Se existir métodos invocados dentro do método seleccionado, é criado para cada
				// invocação, um GraphNode com cor verde e uma respectiva conexão
				ArrayList<MethodInvocation> aux = invocationsForMethods.get(key);
				for (MethodInvocation mi : aux) {
					belowMethods.add(mi);
					GraphNode nodeInvocation = new GraphNode(graph, SWT.NONE, "" + mi);
					nodeInvocation.setBackgroundColor(viewArea.getDisplay().getSystemColor(SWT.COLOR_GREEN));
					new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, nodeKey, nodeInvocation);
				}

				// Métodos acima do nó central (seleccionado)
				// Ciclo que diz respeito à iteração de cada linha do hashmap onde
				// a key == nome da classe e o valor == hashmap (onde a key == declaração do método
				// e o valor == lista de invocações de métodos)
				for (HashMap.Entry<String, HashMap<MethodDeclaration, ArrayList<MethodInvocation>>> entry : classMethods.entrySet()) {
					
					// Ciclo que itera no segundo hashmap (contido no primeiro) cada declaração de métodos
					for (HashMap.Entry<MethodDeclaration, ArrayList<MethodInvocation>> entry2 : entry.getValue().entrySet()) {
						
						// Ciclo que itera cada invocação de métodos em relação a declaração de métodos anterior
						for (MethodInvocation mi2 : classMethods.get(entry.getKey()).get(entry2.getKey())) {
							
							// Compara se existe alguma invocação de método em outras classes em relação ao método
							// seleccionado. Se sim, é criado um GraphNode com o nome do método (declarado) e a
							// classe que pertence
							if(mi2.getName().toString().equals(currentMethod.getName().toString())) {
								aboveMethods.add(entry2.getKey());
								GraphNode nodeInvoke = new GraphNode(graph, SWT.NONE, "" + entry2.getKey().getName().toString() + "\n" + "(" + entry.getKey() + ")");
								nodeInvoke.setBackgroundColor(viewArea.getDisplay().getSystemColor(SWT.COLOR_RED));
								new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, nodeInvoke, nodeKey);
							}
						}
					}
				}
			}
		}

		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(), true);
	}

	/**
	 * 
	 * Cleans all graphNodes and their connections
	 * 
	 * @param g (not-null) Graph
	 */
	private void clearGraph(Graph g) { 

		// Remove all the connections 
		Object[] objects = g.getConnections().toArray(); 
		for (int i = 0; i < objects.length; i++) {
			((GraphConnection) objects[i]).dispose();
		}

		// Remove all the nodes 
		objects = g.getNodes().toArray(); 
		for (int j = 0; j < objects.length; j++) {
			((GraphNode) objects[j]).dispose();
		}
	}

	public void selectMethod(ASTNode node) {
		
	}	

}
