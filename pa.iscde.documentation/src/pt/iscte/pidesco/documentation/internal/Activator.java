package pt.iscte.pidesco.documentation.internal;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import pt.iscte.pidesco.documentation.service.IDocumentationListener;
import pt.iscte.pidesco.documentation.service.IDocumentationServices;

public class Activator implements BundleActivator {

	private static Activator instance;
	private Set<IDocumentationListener> listeners;
	private ServiceRegistration<IDocumentationServices> service;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		listeners = new HashSet<IDocumentationListener>();
		service = context.registerService(IDocumentationServices.class, new DocumentationServicesImpl(), null);
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
