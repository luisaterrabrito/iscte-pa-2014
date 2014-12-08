package pa.iscde.outline.view;

import java.io.File;
import java.util.Map;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.GC;
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

import pa.iscde.outline.utility.ClassOutlineVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

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

			@Override
			public void selectionChanged(File file, String text, int offset,
					int length) {
				System.out.println("File changed");
				OutlineView activeView = OutlineView.getSingleton();

				for (Control control : activeView.viewArea.getChildren()) {
					control.dispose();
				}
				activeView.drawOutlineView(file);
				//activeView.viewArea.pack();
				activeView.viewArea.layout(true);
			//	activeView.viewArea.redraw();
			}

			@Override
			public void fileSaved(File file) {
				OutlineView activeView = OutlineView.getSingleton();
				//activeView.drawOutlineView(file);
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

		ClassOutlineVisitor visitor = new ClassOutlineVisitor();
		javaServices.parseFile(file, visitor);

		Display d = viewArea.getDisplay();

		// ServiceReference<ProjectBrowserServices> ref2 =
		// context.getServiceReference(ProjectBrowserServices.class);
		// browserServices = context.getService(ref2);
		Image i = imageMap.get("package-x-generic.png");
		Image img_escalada = new Image(d, 15, 15);
		GC gc = new GC(img_escalada);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(i, 0, 0, i.getBounds().width, i.getBounds().height, 0, 0,
				15, 15);
		gc.dispose();

		Tree t = new Tree(viewArea, SWT.SINGLE | SWT.BORDER);

		// TreeItem child1 = new TreeItem(t, SWT.NONE, 0);
		// child1.setText("outline");
		// child1.setImage(img_escalada);

		// File f = javaServices.getOpenedFile();
		// Class c = AMinhaView.class;

		// TreeItem child2 = new TreeItem(child1, SWT.NONE, 0);
		// child2.setText("test");
		// child2.setImage(img_escalada);

		TreeItem child2a = new TreeItem(t, SWT.NONE, 0);
		child2a.setText("TestCase");
		child2a.setImage(imageMap.get("class_obj.png"));

		TreeItem child2aI = new TreeItem(child2a, SWT.NONE, 0);
		child2aI.setText("a: int");
		child2aI.setImage(imageMap.get("field_public_obj.png"));

		TreeItem child2aII = new TreeItem(child2a, SWT.NONE, 1);
		child2aII.setText("func() : AguiasAlpiarca");
		child2aII.setImage(imageMap.get("409.png"));

		TreeItem child2aIII = new TreeItem(child2a, SWT.NONE, 2);
		child2aIII.setText("func2() : void");
		child2aIII.setImage(imageMap.get("public_co.gif"));

		TreeItem child2aIV = new TreeItem(child2a, SWT.NONE, 3);
		child2aIV.setText("doStuff() : void");
		child2aIV.setImage(imageMap.get("method_private_obj.png"));

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

		t.addTreeListener(new TreeListener() {
			public void treeExpanded(TreeEvent e) {
				TreeItem ti = (TreeItem) e.item;
				// Abre árvore
			}

			public void treeCollapsed(TreeEvent e) {
				// Fecha árvore
			}
		});

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

	protected static OutlineView getSingleton() {
		return singleton;
	}

}
