package pa.iscde.outline.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.outline.utility.OutlineVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import extensibility.ButtonFilterProvider;

public class OutlineView implements PidescoView {

	private static OutlineView singleton;
	private JavaEditorServices javaServices;
	private Composite viewArea;
	private Map<String, Image> imageMap;
	
	ASTNode currentSelectedNode;
	
	Tree t;

	// private ProjectBrowserServices browserServices;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		rowLayout.fill = true;
		rowLayout.wrap = false;
		rowLayout.pack = true;
		viewArea.setLayout(rowLayout);

		singleton = this;
		this.viewArea = viewArea;
		this.imageMap = imageMap;
		currentSelectedNode = null;

		Bundle bundle = FrameworkUtil.getBundle(JavaEditorServices.class);
		BundleContext context = bundle.getBundleContext();

		ServiceReference<JavaEditorServices> ref = context
				.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);

		javaServices.addListener(new JavaEditorListener() {

			public void updateOutline(File file){
				OutlineView activeView = OutlineView.getSingleton();

				for (Control control : activeView.viewArea.getChildren()) {
					control.dispose();
				}
				activeView.drawOutlineView(file);
				activeView.viewArea.layout(true);
			}

			@Override
			public void selectionChanged(File file, String text, int offset,
					int length) {
				System.out.println("JavaEditorListener.selectionChanged()");
				updateOutline(file);

			}

			@Override
			public void fileSaved(File file) {
				System.out.println("JavaEditorListener.fileSaved()");
				updateOutline(file);

			}

			@Override
			public void fileOpened(File file) {
				//OutlineView activeView = OutlineView.getSingleton();
				//activeView.drawOutlineView(file);
			}

			@Override
			public void fileClosed(File file) {
				// TODO Auto-generated method stub

			}
		});

		drawOutlineView(javaServices.getOpenedFile());
	}

	private void drawOutlineView(File file) {
		if (file == null)
			return;

		ToolBar toolbar = new ToolBar(viewArea, SWT.BAR | SWT.FLAT);
		toolbar.setBounds(0, 0, 200, 70);
		ToolItem toolItem1 = new ToolItem(toolbar, SWT.PUSH);
		toolItem1.setText("Item1");
		for (ButtonFilterProvider filter : getActiveButtonFilters()){
			ToolItem aux = new ToolItem(toolbar, SWT.PUSH);
			aux.setImage(filter.getButtonIcon());
			aux.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					//applyfilter(filter.filterTree(node));
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
		
		t = new Tree(viewArea, SWT.SINGLE | SWT.BORDER | SWT.FILL);
		t.setLayoutData(new RowData(500,500));
		TreeItem rootItem = new TreeItem(t, SWT.NONE, 0);
		t.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		        TreeItem ti = (TreeItem) e.item;
		        currentSelectedNode = (ASTNode) ti.getData();
		      }
		});

		OutlineVisitor visitor = new OutlineVisitor(rootItem, this.imageMap, getActiveButtonFilters()/*,  getCustomIconsSetters */);

		javaServices.parseFile(file, visitor);
	}

	//TODO DELETE
	private void drawOutlineViewOld(File file) {
		if (file == null)
			return;

		Display d = viewArea.getDisplay();

		//		Image i = imageMap.get("package-x-generic.png");
		//		Image img_escalada = new Image(d, 15, 15);
		//		GC gc = new GC(img_escalada);
		//		gc.setAntialias(SWT.ON);
		//		gc.setInterpolation(SWT.HIGH);
		//		gc.drawImage(i, 0, 0, i.getBounds().width, i.getBounds().height, 0, 0,
		//				15, 15);
		//		gc.dispose();

		Tree t = new Tree(viewArea, SWT.SINGLE | SWT.BORDER);
		TreeItem rootItem = new TreeItem(t, SWT.NONE, 0);

		OutlineVisitor visitor = new OutlineVisitor(rootItem, this.imageMap, getActiveButtonFilters()/*,  getCustomIconsSetters */);

		javaServices.parseFile(file, visitor);

	}

	/**
	 * Get all the active button filters
	 * @return all the filters that are enabled at the IDE
	 */
	private ArrayList<ButtonFilterProvider> getActiveButtonFilters() {
		ArrayList<ButtonFilterProvider> _activeFilters = new ArrayList<ButtonFilterProvider>();
		//TODO all the added filters should be here. Upon clicking a button filter, 
		//the filter should be added to this list to be passed to the OutlineVisitor

		//TODO delete SAMPLE filter that doesn't shows void methods
		//		_activeFilters.add(new ButtonFilterProvider() {
		//			
		//			@Override
		//			public Image getButtonIcon() {
		//				// TODO Auto-generated method stub
		//				return null;
		//			}
		//			
		//			@Override
		//			public boolean filterTree(ASTNode node) {
		//				if(node.getClass() == MethodDeclaration.class){
		//					//if not null, the method return type isn't void
		//					if(((MethodDeclaration)node).getReturnType2() != null){
		//						return false;
		//					}
		//				}
		//				return true;
		//			}
		//		});


		return _activeFilters;
	}

	public static OutlineView getSingleton() {
		return singleton;
	}
	
	public Tree getOutlineTree(){
		return t;
	}

	public ASTNode getSelectedTreeItem() {
		return currentSelectedNode;
	}
}
