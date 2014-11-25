package pa.iscde.outline.view;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class AMinhaView implements PidescoView{

	private JavaEditorServices javaServices;
	
	private ProjectBrowserServices browserServices;
	
	
	@Override
	public void createContents(Composite viewArea,Map<String, Image> imageMap) {
		
		Display d = viewArea.getDisplay();
		
		Bundle bundle = FrameworkUtil.getBundle(AMinhaView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
		
		ServiceReference<ProjectBrowserServices> ref2 = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref2);
		
		Tree t = new Tree(viewArea, SWT.SINGLE | SWT.BORDER);
		TreeItem child1 = new TreeItem(t, SWT.NONE, 0);
	    child1.setText("Item 1");

	    File f = javaServices.getOpenedFile();
	    Class c = AMinhaView.class;
	    
	    TreeItem child2 = new TreeItem(t, SWT.NONE, 1);
	    child2.setText("Item 2");
	    Image i = imageMap.get("package-x-generic.png");
	    Image img_escalada = new Image(d, 50, 50);
	    GC gc = new GC(img_escalada);
	    gc.setAntialias(SWT.ON);
	    gc.setInterpolation(SWT.HIGH);
	    gc.drawImage(i, 0, 0, i.getBounds().width, i.getBounds().height, 0, 0, 50, 50);
	    gc.dispose();
	    i.dispose();
	    child2.setImage(img_escalada);
	   
	    
	    TreeItem child2a = new TreeItem(child2, SWT.NONE, 0);
	    child2a.setText("Item 2A");
	    
	    TreeItem child2b = new TreeItem(child2, SWT.NONE, 1);
	    child2b.setText("Item 2B");
	    
	    TreeItem child3 = new TreeItem(t, SWT.NONE, 2);
	    child3.setText("Item 3");
	    
	    t.addTreeListener(new TreeListener() {
	        public void treeExpanded(TreeEvent e) {
	        	TreeItem ti = (TreeItem) e.item;
	          	//Abre árvore
	        }

	        public void treeCollapsed(TreeEvent e) {
	        	//Fecha árvore
	        }
	      });
	    
	    List l = new List(viewArea, SWT.BORDER | SWT.SINGLE);
	    
	    t.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	          TreeItem ti = (TreeItem) e.item;
	          populateList(ti.getText());
	        }

	        private void populateList(String type) {
	          if (type.equals("Item 1")) {
	            l.removeAll();
	            l.add("File 1");
	            l.add("File 2");
	          }
	          if (type.equals("Item 2")) {
	            l.removeAll();
	          }
	          if (type.equals("Item 2A")) {
	            l.removeAll();
	            l.add("File 3");
	            l.add("File 4");
	          }
	          if (type.equals("Item 2B")) {
	            l.removeAll();
	            l.add("File 5");
	            l.add("File 6");
	          }
	          if (type.equals("Item 3")) {
	            l.removeAll();
	            l.add("File 7");
	            l.add("File 8");
	          }

	        }
	      });
	}

}
