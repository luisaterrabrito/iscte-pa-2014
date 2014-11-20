package pa.iscde.tasklist;

import java.io.File;
import java.io.IOException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class TaskActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		TaskView view = new TaskView();
		
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServices = context.getService(ref);
		javaServices.addListener(new JavaEditorListener.Adapter() {
			
			@Override
			public void fileOpened(File file) {
			}
			
			@Override
			public void fileClosed(File file) {
			}
		});
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
	
}
