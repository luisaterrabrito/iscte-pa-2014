package pa.iscde.umldiagram.utils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import java.io.File;
import pa.iscde.umldiagram.UmlView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;

/**
 * 
 * @author Nuno e Diogo
 *
 */
public class UmlActivator implements BundleActivator {
	private ServiceReference<JavaEditorServices> serviceReference;
	
	@Override
	public void start(BundleContext context) throws Exception {
		serviceReference = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServices = context.getService(serviceReference);
		javaServices.addListener(new JavaEditorListener.Adapter() {
			@Override
			public void fileOpened(File file) {
				UmlView umlview = UmlView.getInstance();
				//umlview.newFile(file);
			}
			
			@Override
			public void fileClosed(File file) {
				UmlView umlview = UmlView.getInstance();
				//umlview.fileClose(file);
				
			}
			
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
