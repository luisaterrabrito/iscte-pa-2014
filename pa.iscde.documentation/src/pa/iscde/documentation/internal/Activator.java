package pa.iscde.documentation.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pa.iscde.documentation.service.IDocumentationListener;
import pa.iscde.documentation.service.IDocumentationServices;
import pa.iscde.documentation.view.DocumentationView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * 
 * 
 *
 */
public class Activator implements BundleActivator {

	private static Activator instance;
	private Set<IDocumentationListener> listeners;
	private ServiceRegistration<IDocumentationServices> service;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		listeners = new HashSet<IDocumentationListener>();
		service = context.registerService(IDocumentationServices.class, new DocumentationServicesImpl(), null);

		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServices = context.getService(ref);
		javaServices.addListener(new JavaEditorListener.Adapter() {

			@Override
			public void fileOpened(File file) {
				DocumentationView view = DocumentationView.getInstance();
				view.fillView();
			}

			@Override
			public void fileSaved(File file) {
				DocumentationView view = DocumentationView.getInstance();
				view.fillView();
			}
			
			@Override
			public void selectionChanged(File file, String text, int offset, int length) {
				DocumentationView view = DocumentationView.getInstance();
				view.fillView();
			}
			
			@Override
			public void fileClosed(File file) {
				DocumentationView view = DocumentationView.getInstance();
				view.fillView();
			}

		});
	}
	
	public void stop(BundleContext context) throws Exception {
		instance = null;
		service.unregister();
	}
	
	public static Activator getInstance() {
		return instance;
	}
	
	public Set<IDocumentationListener> getListeners() {
		return listeners;
	}
	
	public void addListener(IDocumentationListener l) {
		listeners.add(l);
	}

	public void removeListener(IDocumentationListener l) {
		listeners.remove(l);
	}

}
