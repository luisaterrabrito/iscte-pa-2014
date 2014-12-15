package pa.iscde.outline.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
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

	// private ProjectBrowserServices browserServices;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		singleton = this;
		this.viewArea = viewArea;
		this.imageMap = imageMap;

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
		
		Tree t = new Tree(viewArea, SWT.SINGLE | SWT.BORDER);
		TreeItem rootItem = new TreeItem(t, SWT.NONE, 0);

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
		
//		int coisocoiso = 0;
		
//		for(String s : visitor.getNames()){
//			TreeItem aux = new TreeItem(t, SWT.NONE, coisocoiso);
//			aux.setText(s);
//			coisocoiso++;
//		}
		
		// TreeItem child1 = new TreeItem(t, SWT.NONE, 0);
		// child1.setText("outline");
		// child1.setImage(img_escalada);

		// File f = javaServices.getOpenedFile();
		// Class c = AMinhaView.class;

		// TreeItem child2 = new TreeItem(child1, SWT.NONE, 0);
		// child2.setText("test");
		// child2.setImage(img_escalada);
//
//		TreeItem child2a = new TreeItem(t, SWT.NONE, 0);
//		child2a.setText("TestCase");
//		child2a.setImage(imageMap.get("class_obj.png"));
//
//		TreeItem child2aI = new TreeItem(child2a, SWT.NONE, 0);
//		child2aI.setText("a: int");
//		child2aI.setImage(imageMap.get("field_public_obj.png"));
//
//		TreeItem child2aII = new TreeItem(child2a, SWT.NONE, 1);
//		child2aII.setText("func() : AguiasAlpiarca");
//		child2aII.setImage(imageMap.get("409.png"));
//
//		TreeItem child2aIII = new TreeItem(child2a, SWT.NONE, 2);
//		child2aIII.setText("func2() : void");
//		child2aIII.setImage(imageMap.get("public_co.gif"));
//
//		TreeItem child2aIV = new TreeItem(child2a, SWT.NONE, 3);
//		child2aIV.setText("doStuff() : void");
//		child2aIV.setImage(imageMap.get("method_private_obj.png"));

		// TreeItem child3 = new TreeItem(child1, SWT.NONE, 1);
		// child3.setText("view");
		// child3.setImage(img_escalada);
		//
		// TreeItem child3a = new TreeItem(child3, SWT.NONE, 0);
		// child3a.setText("AMinhaView.java");
		// child3a.setImage(imageMap.get("class_obj.png"));
		//
		// TreeItem child3aI = new TreeItem(child3a, SWT.NONE, 0);
		// child3aI.setText("Ninguém vai ver isto na print");
		// //imagem para variável
//
//		t.addTreeListener(new TreeListener() {
//			
//			public void treeExpanded(TreeEvent e) {
//				TreeItem ti = (TreeItem) e.item;
//				// Abre árvore
//			}
//
//			public void treeCollapsed(TreeEvent e) {
//				// Fecha árvore
//			}
//		});
//
		// List l = new List(viewArea, SWT.BORDER | SWT.SINGLE);
		//
		// t.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent e) {
		// TreeItem ti = (TreeItem) e.item;
		// populateList(ti.getText());
		// }
		//
		// private void populateList(String type) {
		// if (type.equals("Item 1")) {
		// l.removeAll();
		// l.add("File 1");
		// l.add("File 2");
		// }
		// if (type.equals("Item 2")) {
		// l.removeAll();
		// }
		// if (type.equals("Item 2A")) {
		// l.removeAll();
		// l.add("File 3");
		// l.add("File 4");
		// }
		// if (type.equals("Item 2B")) {
		// l.removeAll();
		// l.add("File 5");
		// l.add("File 6");
		// }
		// if (type.equals("Item 3")) {
		// l.removeAll();
		// l.add("File 7");
		// l.add("File 8");
		// }
		//
		// }
		// });

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

	protected static OutlineView getSingleton() {
		return singleton;
	}
}
