package pt.iscte.pidesco.projectbrowser.internal;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.projectbrowser.extensibility.ProjectBrowserFilter;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;

public class ProjectBrowserView implements PidescoView {

	private static final String VIEW_ID = "pt.iscte.pidesco.projectbrowser.tree";
	
	private static final String EXT_POINT_FILTER = "pt.iscte.pidesco.projectbrowser.filter";
	
	private static ProjectBrowserView instance;
	
	private TreeViewer tree;
	private PackageElement invisibleRoot;

	private File rootPath;
	
	private Image packageIcon;
	private Image classIcon;

	private Map<String, Filter> filtersMap;
	private Set<String> activeFilters;
	
	private static PidescoServices services;
	
	public ProjectBrowserView() {
		filtersMap = new HashMap<String, ProjectBrowserView.Filter>();
		activeFilters = new HashSet<String>();
		loadFilters();
		
	    BundleContext context = FrameworkUtil.getBundle(ProjectBrowserListener.class).getBundleContext();
	    ServiceReference<PidescoServices> ref = context.getServiceReference(PidescoServices.class);
	    services = context.getService(ref);
	}
	
	private void loadFilters() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_FILTER).getExtensions()) {
			
			ProjectBrowserFilter f = null;
			try {
				f = (ProjectBrowserFilter) ext.getConfigurationElements()[0].createExecutableExtension("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(f != null)
				filtersMap.put(ext.getUniqueIdentifier(), new Filter(f));
		}
	}

	public PackageElement getRootPackage() {
		return invisibleRoot;
	}
	
	public void activateFilter(String id) {
		if(filtersMap.containsKey(id)) {
			activeFilters.add(id);
			tree.addFilter(filtersMap.get(id));
			tree.expandAll();
		}
		else {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Filter not found", "Filter with id=" + id + " not found");
		}
	}
	
	public void deactivateFilter(String id) {
		if(filtersMap.containsKey(id)) {
			activeFilters.remove(id);
			tree.removeFilter(filtersMap.get(id));
			tree.expandAll();
		}
		else {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Filter not found", "Filter with id=" + id + " not found");
		}
	}
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> images) {
		instance = this;
		rootPath = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
		packageIcon = images.get("package.gif");
		classIcon = images.get("class.gif");
		tree = new TreeViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new ViewContentProvider());
		tree.setLabelProvider(new ViewLabelProvider());
		
		addDoubleClickListener();
		addSelectionListener();
		refresh();
	}

	public void refresh() {
		Object[] expanded = tree.getExpandedElements();

		invisibleRoot = scan(rootPath);
		tree.setInput(invisibleRoot);
		
		if(!activeFilters.isEmpty())
			tree.expandAll();
		else
			for(Object o : expanded)
				tree.expandToLevel(o, TreeViewer.ALL_LEVELS);
	}


	public static ProjectBrowserView getInstance() {
		if(instance == null)
			services.openView(VIEW_ID);
			
		return instance;
	}
	


	private static class Filter extends ViewerFilter {
		private final ProjectBrowserFilter filter;
		
		public Filter(ProjectBrowserFilter filter) {
			this.filter = filter;
		}
		
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			PackageElement parent = parentElement == null ? null : (PackageElement) parentElement;
			SourceElement child = (SourceElement) element;
			return filter.include(child, parent);
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


	private static PackageElement scan(File root) {
		PackageElement pack = new PackageElement(null, "", root);
		for(File child : root.listFiles()) {
			if(!child.getName().startsWith("."))
				scanRec(child, pack);
		}
		return pack;
	}

	private static void scanRec(File f, PackageElement p) {
		if(f.isFile() && f.getName().endsWith(".java")) {
			new ClassElement(p, f);
		}
		else if(f.isDirectory()) {
			PackageElement childPack = new PackageElement(p, f.getName(), f);
			for(File child : f.listFiles()) {
				scanRec(child, childPack);
			}
		}
	}


	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			return ((SourceElement) obj).isPackage() ? packageIcon : classIcon;
		}
	}



	private void addDoubleClickListener() {
		tree.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection s = (IStructuredSelection) tree.getSelection();
				if(s.size() == 1) {
					SourceElement e = (SourceElement) s.getFirstElement();
					for(ProjectBrowserListener l : ProjectBrowserActivator.getInstance().getListeners()) {
						l.doubleClick(e);
					}
				}
			}
		});
	}
	
	private void addSelectionListener() {
		tree.addSelectionChangedListener(new ISelectionChangedListener() {	
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				List<SourceElement> list = Collections.unmodifiableList(selection.toList());
				for(ProjectBrowserListener l : ProjectBrowserActivator.getInstance().getListeners()) {
					l.selectionChanged(list);
				}
			}
		});
	}

	
}
