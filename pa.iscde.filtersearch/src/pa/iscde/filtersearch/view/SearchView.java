package pa.iscde.filtersearch.view;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
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

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchView implements PidescoView {

	private static Composite viewArea;
	private static SearchView instance;
	private ProjectBrowserServices browserServices;
	private static Set<String> _list ;


	private TreeViewer tree;

	private Text searchText;

	private Text results;

	private Image packageIcon;
	private Image classIcon;
	
	public SearchView() {
		Bundle bundle = FrameworkUtil.getBundle(SearchView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<ProjectBrowserServices> ref2 = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref2);
		_list = new HashSet<String>();

	}

	public static SearchView getInstance(){
		return instance;
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {
		SearchView.viewArea = viewArea;
		final Shell shell = new Shell();
		instance = this;

		packageIcon = images.get("murilhas.png");
		classIcon = images.get("almas.png");
		
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
		filter.add("Variables");
		filter.add("Methods");
		filter.add("Classes");
		filter.add("Packages");
		filter.add("All");


		// Results

		label = new Label(viewArea, SWT.NULL);
		label.setText("Results:");

		results = new Text(viewArea,SWT.WRAP| SWT.MULTI| SWT.BORDER| SWT.H_SCROLL| SWT.V_SCROLL);
		gridData =  new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		gridData.grabExcessVerticalSpace = true;
		
		ModifyListener modifyListener = new ModifyListener() {
		   
			@Override
			public void modifyText(ModifyEvent e) {
				String text = "";

				if(searchText.getText().isEmpty()){
					for (String s : _list) {
							text += s + '\n';
					}
				}else{
					for (String s : _list) {
						if(s.contains(searchText.getText()))
							text += s + '\n';
					}
				}

					results.setText(text);
			}
		};

		searchText.addModifyListener(modifyListener);

		results.setLayoutData(gridData);


		PackageElement root = browserServices.getRootPackage();

		tree = new TreeViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new ViewContentProvider());
		tree.setLabelProvider(new ViewLabelProvider());
		
		PackageElement invisibleRoot = getPackage();
		
		String s ="";
		for(SourceElement e : root.getChildren()){

			if(e.isPackage()) {
				s += e.getName() + '\n';
				_list.add(e.getName());
				PackageElement pack = new PackageElement(null, "", e.getFile());
				for(File child : e.getFile().listFiles()) {
					if(!child.getName().startsWith("."))
						s+= scanRec(child, pack);
				}

				results.setText(s);
			}
		}




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
				if(event.widget == searchButton){
					MessageBox dialog =  new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
					dialog.setText("SEARCH CONFIRMATION");
					dialog.setMessage("Do you really want to do this?");
					//					results.setText(browserServices.searchMethod(searchText.getText()));
					// open dialog and await user selection
					

						dialog.open(); 
					}
				}
			};

			searchButton.addListener(SWT.Selection, listener);

		}

		private PackageElement getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

		public void addText(String string) {
			results.setText(string);
		}



		private static String scanRec(File f, PackageElement p) {
			String s = "";
			if(f.isFile() && f.getName().endsWith(".java")) {
				s += '\t' +  f.getName() + '\n';
				_list.add(f.getName());
			}
			else if(f.isDirectory()) {
				PackageElement childPack = new PackageElement(p, f.getName(), f);
				for(File child : f.listFiles()) {
					scanRec(child, childPack);
				}
			}
			return s;
		}

		
		/**
		 * 
		 * 
		 * 		OUTRAS CLASSES
		 * 
		 * 
		 * @author LuisMurilhas
		 *
		 */
		
		
		class ViewLabelProvider extends LabelProvider {

			public String getText(Object obj) {
				return obj.toString();
			}
			public Image getImage(Object obj) {
				return ((SourceElement) obj).isPackage() ? packageIcon : classIcon;
			}
		}	
		
		private static class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

			private static final Object[] EMPTY = new Object[0];
			
			public void inputChanged(Viewer v, Object oldInput, Object newInput) {

			}

			public void dispose() {

			}

			public Object[] getElements(Object parent) {
				return getChildren(parent);

			}

			public Object getParent(Object child) {
				return ((SourceElement)child).getParent();
			}

			public Object[] getChildren(Object parent) {
				if (parent instanceof PackageElement)
					return ((PackageElement)parent).getChildren().toArray();
				else
					return EMPTY;
			}

			public boolean hasChildren(Object parent) {
				return parent instanceof PackageElement && ((PackageElement)parent).hasChildren();
			}

		}

		
		
		
	}
