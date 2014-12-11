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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.externalImages.ExternalImage;
import pa.iscde.filtersearch.providers.ProjectBrowserSearchProvider;
import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.filtersearch.providers.ViewContentProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;


public class SearchView implements PidescoView {

	private final static String NO_RESULTS_FOUND = "No results found.";

	private List<SearchProvider> providers = new ArrayList<>();
	private List<SearchCategory> categories = new ArrayList<>();
	private Map<SearchProvider, ExternalImage> providerAndImageMap = new HashMap<SearchProvider, ExternalImage>();
	private Map<Object, SearchProvider> provideAndObjectMap = new HashMap<Object, SearchProvider>();

	private TreeViewer tree;	
	private Text searchText;

	private PidescoServices pidescoServices;


	/**
	 * Construtor da classe
	 */
	public SearchView() {

		Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
		
		getProviders();

	}


	/**
	 * Acede aos pontos de extensão que implementam a interface SearchProvider e adiciona-os a uma lista
	 */
	private void getProviders() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = reg.getExtensionPoint("pa.iscde.filtersearch.SearchProvider");
		IExtension[] extensions = extensionPoint.getExtensions();

		SearchProvider p = null;
		String iconName = null;
		
		for(IExtension ext : extensions){
			for(IConfigurationElement configurationElement : ext.getConfigurationElements()){
				try {
					p = (SearchProvider) configurationElement.createExecutableExtension("SearchProvider");
					iconName = configurationElement.getAttribute("iconName");
				} catch (CoreException e) {
					e.printStackTrace();
				}
				providers.add(p);
				providerAndImageMap.put(p, new ExternalImage(iconName,ext.getContributor().getName()));
			}
		}
	}


	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 8;
		viewArea.setLayout(gridLayout);

		searchLabel(viewArea);
		clearButtonLabel(viewArea, images);
		treeLabel(viewArea);

	}

	/**
	 * Cria a componente gráfico correspondente ao campo de pesquisa
	 * 
	 * @param viewArea
	 */
	private void searchLabel(Composite viewArea) {
		Label label = new Label(viewArea, SWT.NULL);
		label.setText("Search: ");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(gridData);
		gridData.horizontalSpan = 2;

		searchText = new Text(viewArea, SWT.SINGLE | SWT.BORDER);
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		searchText.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {

				if(event.type == SWT.Modify){
					Text text = (Text)event.widget;
					loadCategories(text.getText());
				}

			}
		});
	}


	/**
	 * Cria a componente gráfica correspondente ao botão "clear"
	 * 
	 * @param viewArea
	 * @param images
	 */
	private void clearButtonLabel(Composite viewArea, Map<String, Image> images) {
		Button clearButton = new Button(viewArea, SWT.RIGHT | SWT.NO_BACKGROUND);
		clearButton.setImage(images.get("clear.gif"));

		clearButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(event.type == SWT.Selection)
					if(!searchText.getText().isEmpty())
						searchText.setText("");
			}
		});
	}

	/**
	 * Cria a compoente gráfica corresponde à arvore de resultados
	 * 
	 * @param viewArea
	 */
	private void treeLabel(Composite viewArea) {
		tree = new TreeViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new ViewContentProvider());
		tree.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider()));
		loadCategories("");
		tree.expandAll();

		GridData gridData = new GridData(GridData.FILL_BOTH | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.getTree().setLayoutData(gridData);
		gridData.horizontalSpan =2;

		tree.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection s = (IStructuredSelection) tree.getSelection();
				if(s.size() == 1) {
					Object element = s.getFirstElement();
					SearchProvider provider = provideAndObjectMap.get(element);
					provider.doubleClickAction(tree, element);
				}
			}
		});
	}

	/**
	 * Carrega todas as categorias (nome do projecto) que utilizaram o ponto de extensão "SearchProvider".
	 * Além disso, para cada objecto presente na lista de resultados de cada categoria, atribuí o provider correspondente e faz
	 * um mapeamento dessa ligação entre objecto e provider num hashmap.
	 * @param text
	 */

	private void loadCategories(String text) {

		categories.clear();

		for(Map.Entry<SearchProvider, ExternalImage> entry : providerAndImageMap.entrySet()){
			
			
			SearchCategory category = new SearchCategory(entry.getKey().getClass().getSimpleName(), pidescoServices.getImageFromPlugin(entry.getValue().getPlugin(), entry.getValue().getImageName()));
			
			System.out.println("--------");
			System.out.println(entry.getKey().getClass().getSimpleName());
			System.out.println(entry.getValue().getImageName());
			
			category.hits = entry.getKey().getResults(text);
			for(Object o : category.hits){
				provideAndObjectMap.put(o, entry.getKey());
			}
			if(!category.hits.isEmpty())
				categories.add(category);
		}
		if(categories.isEmpty())
			categories.add(new SearchCategory(NO_RESULTS_FOUND, null));

		tree.setInput(categories);
		tree.expandAll();
	}

	
	
	
	

	/**
	 *  ##########################################
	 * 				OUTRAS CLASSES
	 *  ##########################################
	 */


	/**
	 * Classe onde é definido que elementos gráficos devem aparecer no plugin, tais como os ícones de cada componente da árvore.
	 * É também responsável pelo desenho do efeito highlight nos resultados de pesquisa, consoante o texto de entrada.
	 * 
	 * @authors LuisMurilhas & DavidAlmas
	 */
	public class ViewLabelProvider implements DelegatingStyledCellLabelProvider.IStyledLabelProvider{


		/**
		 * Expressão regular que coloca os resultados a highlight, quando estes possuem texto igual ao introduzido na pesquisa. 
		 */
		@Override
		public StyledString getStyledText(Object element) {

			StyledString text = new StyledString();
			String[] values = element.toString().split("(?i)"+searchText.getText());

			if(values.length == 0 )
				text.append(searchText.getText(),new StylerHighlighter());

			for (int i = 0; i < values.length; i++) {
				text.append(values[i].toString());
				if(i < values.length - 1 || (values.length == 1 && element.toString().contains(searchText.getText())))
					text.append(searchText.getText(),new StylerHighlighter());
			}

			return text;
		}


		/**
		 * Recebe a imagem que tem que desenhar, para o objecto passado como argumento
		 */
		public Image getImage(Object object) {

			if(object instanceof SearchCategory) {
				for(SearchCategory category : categories){
					if(object.equals(category))
						return category.getIcon();
				}
			}
			else {
				for(Map.Entry<Object, SearchProvider> entry : provideAndObjectMap.entrySet()){
					if(entry.getKey().equals(object))
						return entry.getValue().setImage(object);
				}
			}

			return null;
		}

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
	}	

	/**
	 * Classe responsável por definir o estido do highlight 
	 * 
	 * @authors LuisMurilhas & DavidAlmas
	 *
	 */
	class StylerHighlighter extends Styler{
		public void applyStyles(TextStyle textStyle) {
			textStyle.background = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
		}
	}
}