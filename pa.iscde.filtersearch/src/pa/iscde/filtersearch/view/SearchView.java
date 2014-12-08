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
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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
	private static Text searchText;

	private final static String NO_RESULTS_FOUND = "No results found.";

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

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 8;
		viewArea.setLayout(gridLayout);

		// Search
		Label label = new Label(viewArea, SWT.NULL);
		label.setText("Search: ");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(gridData);
		gridData.horizontalSpan = 2;


		searchText = new Text(viewArea, SWT.SINGLE | SWT.BORDER);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button btn = new Button(viewArea, SWT.RIGHT | SWT.NO_BACKGROUND);
		btn.setImage(images.get("clear.gif"));


		SelectionListener selectionListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!searchText.getText().isEmpty())
				searchText.setText("");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};


		btn.addSelectionListener(selectionListener);

		// TreeViewer
		rootPackage = browserServices.getRootPackage();
		tree = new TreeViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new ViewContentProvider());
		tree.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
		loadCategories("");
		tree.expandAll();

		gridData = new GridData(GridData.FILL_BOTH | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.getTree().setLayoutData(gridData);
		gridData.horizontalSpan =2;

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
			if(!category.hits.isEmpty())
				categories.add(category);
		}
		if(categories.isEmpty())
			categories.add(new SearchCategory(NO_RESULTS_FOUND));

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
	class ViewLabelProvider implements DelegatingStyledCellLabelProvider.IStyledLabelProvider{

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		/***
		 * Expressão regular para colocar o conteudo da string que procuramos a highlight .. 
		 * ainda contêm mas já serve para o efeito
		 */
		public StyledString getStyledText(Object element) {

			StyledString text = new StyledString();

			String[] values = element.toString().split("(?i)"+searchText.getText());

			if(values.length == 0 )
				text.append(searchText.getText(),new StylerHighlighter());


			for (int i = 0;i < values.length;i++) {
				text.append(values[i].toString());
				if(i < values.length - 1 || (values.length == 1 && element.toString().contains(searchText.getText())))
					text.append(searchText.getText(),new StylerHighlighter());
			}

			return text;
		}

		@Override
		public Image getImage(Object element) {
			if(element instanceof SearchCategory)
				return categoryIcon;
			else if(element instanceof PackageElement)
				return packageIcon;
			else if(element instanceof ClassElement)
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

	class StylerHighlighter extends Styler{
		public void applyStyles(TextStyle textStyle) {
			textStyle.background = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
		}
	}




}