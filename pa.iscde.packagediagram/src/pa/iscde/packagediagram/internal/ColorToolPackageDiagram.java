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
		
	}

}
