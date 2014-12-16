package pa.iscde.umldiagram.drbps.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.io.File;
import java.util.Collection;

import pa.iscde.umldiagram.drbps.UmlView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * 
 * @author Nuno e Diogo
 *
 */
public class UmlActivator implements BundleActivator {
	private ServiceReference<ProjectBrowserServices> serviceReference;
	private ProjectBrowserServices browserServices;
	
	@Override
	public void start(BundleContext context) throws Exception {
		serviceReference = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(serviceReference);
		browserServices.addListener(new ProjectBrowserListener.Adapter() {
			
			@Override
			public void selectionChanged(Collection<SourceElement> selection) {
				
				UmlView umlview = UmlView.getInstance();
				umlview.clearGraph();
				umlview.paintUml(selection);
				umlview.runActionSelection(selection);
			}
		});
			
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
