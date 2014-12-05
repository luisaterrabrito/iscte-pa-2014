package pa.iscde.filtersearch.view;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchView implements PidescoView {


	List<SearchProvider> providers = new ArrayList<>(); 
	Map<Object, SearchProvider> providerMap;

	@SuppressWarnings("unused")
	private static Composite viewArea;
	private static SearchView instance;

	private ProjectBrowserServices browserServices;
	private JavaEditorServices editorServices;

	@SuppressWarnings("unused")
	private PackageElement rootPackage;
	private TreeViewer tree;	
	private static Image packageIcon;
	private static Image classIcon;
	private static Image categoryIcon;

	private Text searchText;

	public SearchView() {
		Bundle bundle = FrameworkUtil.getBundle(SearchView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<ProjectBrowserServices> serviceReference_projectBrowser = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(serviceReference_projectBrowser);




		/**
		 * Acede aos pontos de extensï¿½o que implementam a interface SearchProvider
		 */
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = reg.getExtensionPoint("pa.iscde.filtersearch.SearchProvider");
		IExtension[] extensions = extensionPoint.getExtensions();

		for(IExtension ext : extensions){
			for(IConfigurationElement configurationElement : ext.getConfigurationElements()){
				SearchProvider p = null;
				try {
					p = (SearchProvider) configurationElement.createExecutableExtension("className");
				} catch (CoreException e) {
					e.printStackTrace();
				}
				providers.add(p);
			}
		}

	}

	public static SearchView getInstance(){
		return instance;
	}


	class SearchCategory {
		String name;
		List<Object> hits = new ArrayList<>();

		SearchCategory(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name; 
		}
	}





	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {

		SearchView.viewArea = viewArea;
		instance = this;
		final Shell shell = new Shell();

		packageIcon = images.get("package_obj.gif");
		classIcon = images.get("classes.gif");
		categoryIcon = images.get("searchtool.gif");

		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.verticalSpacing = 8;
		viewArea.setLayout(gridLayout);

		// Search
		Label label = new Label(viewArea, SWT.NULL);
		label.setText("Search: ");

		searchText = new Text(viewArea, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		searchText.setLayoutData(gridData);


		// Filter
		label = new Label(viewArea, SWT.NULL);
		label.setText("Filter");

		Combo filter = new Combo(viewArea, SWT.READ_ONLY);
		filter.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		filter.add("All");
		filter.add("Public");
		filter.add("Private");

		// Results
		label = new Label(viewArea, SWT.NULL);
		label.setText("Results:");


		// TreeViewer
		rootPackage = browserServices.getRootPackage();
		tree = new TreeViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new ViewContentProvider());
		tree.setLabelProvider(new ViewLabelProvider());
		loadCategories();
		tree.expandAll();


		tree.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {


				IStructuredSelection s = (IStructuredSelection) tree.getSelection();
				if(s.size() == 1) {

					Object element = s.getFirstElement();
					SearchProvider provider = providerMap.get(element);

					provider.doubleClickAction(tree, element);

				}
			}
		});


		// Listener de alterações no searchText

		ModifyListener modifyListener = new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				loadCategories();
			}
		};

		searchText.addModifyListener(modifyListener);


		// Search Button

		final Button searchButton = new Button(viewArea, SWT.PUSH);
		searchButton.setText("Enter");

		gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = GridData.END;
		searchButton.setLayoutData(gridData);

		Listener listener = new Listener(){
			@Override
			public void handleEvent(Event event) {

				if(!searchText.getText().isEmpty()){
					loadCategories();

				} else{
					MessageBox dialog =  new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					dialog.setText("ERROR");
					dialog.setMessage("Please enter some text!");
					dialog.open(); 
				}
			}

		};

		searchButton.addListener(SWT.Selection, listener);

	}

	private void loadCategories() {
		providerMap = new HashMap<Object, SearchProvider>();
		List<SearchCategory> categories = new ArrayList<>();
		for(SearchProvider p : providers) {
			SearchCategory category = new SearchCategory(p.getClass().getSimpleName());
			category.hits = p.getResults(searchText.getText());
			for(Object o : category.hits){
				providerMap.put(o, p);
			}
			categories.add(category);
		}
		tree.setInput(categories);
		tree.expandAll();
	}



	/**
	 *  ##########################################
	 * 				OUTRAS CLASSES
	 *  ##########################################
	 *
	 */


	public static class ProjectBrowserSearchProvider implements SearchProvider {

		private ProjectBrowserServices browserServices;
		private JavaEditorServices editorServices;
		private ViewLabelProvider labelProvider;

		public ProjectBrowserSearchProvider() {

			Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
			BundleContext context  = bundle.getBundleContext();
			ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
			browserServices = context.getService(ref);

			ServiceReference<JavaEditorServices> serviceReference_javaEditor = context.getServiceReference(JavaEditorServices.class);
			editorServices = context.getService(serviceReference_javaEditor);
		}

		@Override
		public List<Object> getResults(String text) {
			List<Object> hits = new ArrayList<>();
			PackageElement root = browserServices.getRootPackage();



			scan(root,hits,text);

			return hits;
		}

		private void scan(PackageElement root, List<Object> hits, String text) {



			Map<ClassElement, String> resultsMethods = new HashMap<ClassElement, String>();

			for(SourceElement c : root){

				if(c.getName().toUpperCase().contains(text.toUpperCase()))
					hits.add(c);

				if(c.isClass()){
					verifyExistingMethodsInsideClass((ClassElement)c, text, resultsMethods);
				} else {
					scan((PackageElement)c,hits, text);				
				}
			}
		}

		/** 
		 *
		 * TODO
		 * 
		 * acabar a verificação da existencia de métodos
		 *
		 */
		private void verifyExistingMethodsInsideClass(ClassElement c, String text, Map<ClassElement, String> resultsMethods) {



			Scanner fileScanner = null;
			boolean fileEnd = false;

			File f  = c.getFile();
			
			System.out.println("File name: " + c.getFile().getName());
			try { 
				fileScanner = new Scanner(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			while(!fileEnd){

				if(fileScanner.hasNextLine()){

					Scanner lineScanner = new Scanner(fileScanner.nextLine());

					if(lineScanner.hasNextLine()){

						String line = lineScanner.nextLine();
						
						System.out.println(line);

						if(line.contains(text) && !text.isEmpty()){
							resultsMethods.put(c, line);
							System.out.println("Tenho um resultado");
						}

					} else {
						fileEnd = true;
						printMap(resultsMethods);
					}
				}
			}
		}

		private void printMap(Map<ClassElement, String> resultsMethods) {
			System.out.println("--------------- RESULTS ---------------");
			for (Map.Entry<ClassElement, String> entry : resultsMethods.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
		}

		@Override
		public Image setImage(Object object) {
			return labelProvider.getImage(object);
		}

		@Override
		public void doubleClickAction(TreeViewer tree, Object object) {

			if (object instanceof ClassElement){
				SourceElement element = (SourceElement) object;
				File f = element.getFile();
				editorServices.openFile(f);
			}
		}

	}


	/**
	 * Decide o que vai aparecer em cada item da árvore: nome e imagem
	 * @author lcmms
	 *
	 */
	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			if(obj instanceof SearchCategory)
				return categoryIcon;
			else if(obj instanceof PackageElement)
				return packageIcon;
			else if(obj instanceof ClassElement)
				return classIcon;
			return null;
		}
	}	


	/**
	 * Define que elementos vï¿½o aparecer na ï¿½rvore
	 * @author lcmms
	 *
	 */

	private static class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {

		}

		public void dispose() {

		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object input) {
			List<SearchCategory> cats = (List<SearchCategory>) input;
			return cats.toArray();

		}

		public Object getParent(Object child) {
			//			SearchCategory cat = (SearchCategory) child;
			return null;

		}

		public Object[] getChildren(Object parent) {
			SearchCategory cat = (SearchCategory) parent;
			return cat.hits.toArray();
		}

		public boolean hasChildren(Object parent) {
			if(!(parent instanceof SearchCategory))
				return false;

			SearchCategory cat = (SearchCategory) parent;
			return !cat.hits.isEmpty();
		}

	}




}