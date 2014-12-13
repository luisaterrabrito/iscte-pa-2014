package pa.iscde.packagediagram.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoTool;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


public class ColorToolPackageDiagram implements PidescoTool {

	@Override
	public void run(boolean activate) {
		 
		 PackageDiagramView.getInstance().loadColorMenu();
		
//		 Menu menu = new Menu(PackageDiagramView.getInstance().getShell(), SWT.POP_UP);
//	      MenuItem exit = new MenuItem(menu, SWT.NONE);
//	      exit.setText("Hello! This is ");
//	      exit = new MenuItem(menu, SWT.NONE);
//	      exit.setText("Menu");
//	      exit = new MenuItem(menu, SWT.NONE);
//	      exit.setText("Cool");
//	      exit.addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("Coool");
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	      menu.setVisible(true);
	}

}
