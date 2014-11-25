package pt.iscte.pidesco.documentation.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pt.iscte.pidesco.documentation.service.IDocumentationListener;
import pt.iscte.pidesco.documentation.service.IDocumentationServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class DocumentationActivator implements BundleActivator {

	private static DocumentationActivator instance;
	private Set<IDocumentationListener> listeners;
	private ServiceRegistration<IDocumentationServices> service;
	
	//não deveria ser singleton?
	
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
				view.draw();
			}
			
			@Override
			public void fileClosed(File file) {
				DocumentationView view = DocumentationView.getInstance();
				view.draw();
//				view.cleanView();
			}
		});

	}
	
	public void stop(BundleContext context) throws Exception {
		instance = null;
		service.unregister();
	}
	
	public static DocumentationActivator getInstance() {
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
