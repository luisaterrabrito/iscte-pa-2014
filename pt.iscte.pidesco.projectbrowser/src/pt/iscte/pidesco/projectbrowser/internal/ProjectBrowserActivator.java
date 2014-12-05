package pt.iscte.pidesco.projectbrowser.internal;

import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ProjectBrowserActivator implements BundleActivator {

	private static ProjectBrowserActivator instance;
	private Set<ProjectBrowserListener> listeners;
	private ServiceRegistration<ProjectBrowserServices> service;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		listeners = new HashSet<ProjectBrowserListener>();
		service = context.registerService(ProjectBrowserServices.class, new ProjectBrowserServicesImpl(), null);
	}

	public void stop(BundleContext context) throws Exception {
		instance = null;
		service.unregister();
		listeners.clear();
	}
	
	public static ProjectBrowserActivator getInstance() {
		return instance;
	}
	
	public Set<ProjectBrowserListener> getListeners() {
		return listeners;
	}
	
	public void addListener(ProjectBrowserListener l) {
		listeners.add(l);
	}

	public void removeListener(ProjectBrowserListener l) {
		listeners.remove(l);
	}
	
	
}
