package pa.iscde.filtersearch.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchView implements PidescoView {


	List<SearchProvider> providers = new ArrayList<>(); 
	Map<Object, SearchProvider> providerMap;

	@SuppressWarnings("unused")
	private static Composite viewArea;

	private ProjectBrowserServices browserServices;

	@SuppressWarnings("unused")
	private PackageElement rootPackage;
	private TreeViewer tree;	
	private static Image packageIcon;
	private static Image classIcon;
	private static Image categoryIcon;

	public SearchView() {
		Bundle bundle = FrameworkUtil.getBundle(SearchView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<ProjectBrowserServices> serviceReference_projectBrowser = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(serviceReference_projectBrowser);



		/**
		 * Acede aos pontos de extensão que implementam a interface SearchProvider
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

		packageIcon = images.get("package_obj.gif");
		classIcon = images.get("classes.gif");
		categoryIcon = images.get("searchtool.gif");

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.verticalSpacing = 8;
		viewArea.setLayout(gridLayout);

		// Search
		Label label = new Label(viewArea, SWT.NULL);
		label.setText("Search: ");

		Text searchText = new Text(viewArea, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		searchText.setLayoutData(gridData);

		// TreeViewer
		rootPackage = browserServices.getRootPackage();
		tree = new TreeViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new ViewContentProvider());
		tree.setLabelProvider(new ViewLabelProvider());
		loadCategories("");
		tree.expandAll();

		tree.getTree().setLayoutData(new GridData(GridData.FILL_BOTH | SWT.H_SCROLL | SWT.V_SCROLL));

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

				Text text = (Text)e.widget;
				loadCategories(text.getText());
			}
		};

		searchText.addModifyListener(modifyListener);

	}

	private void loadCategories(String text) {
		providerMap = new HashMap<Object, SearchProvider>();
		List<SearchCategory> categories = new ArrayList<>();
		for(SearchProvider p : providers) {
			SearchCategory category = new SearchCategory(p.getClass().getSimpleName());
			category.hits = p.getResults(text);
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
	 * Define que elementos vão aparecer na árvore
	 *
	 */

	private static class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {

		}

		public void dispose() {

		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object input) {
			List<SearchCategory> categories = (List<SearchCategory>) input;
			return categories.toArray();

		}

		/**
		 * É necessário
		 */
		public Object getParent(Object child) {
			// SearchCategory cat = (SearchCategory) child;
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