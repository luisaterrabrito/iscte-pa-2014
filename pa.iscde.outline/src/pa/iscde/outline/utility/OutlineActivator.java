package pa.iscde.outline.utility;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;


public class OutlineActivator implements BundleActivator{

	private static OutlineActivator instance;

	private BundleContext context;
	
	private Set<OutlineListener> listeners;
	
	private OutlineServices services;	
	
	public static OutlineActivator getInstance() {
		return instance;
	}
	
	public OutlineServices getServices() {
		return services;
	}

	public OutlineActivator() {
		listeners = new HashSet<OutlineListener>();
	}

	public void addListener(OutlineListener l) {
		listeners.add(l);
	}

	public void removeListener(OutlineListener l) {
		listeners.remove(l);
	}
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		services = new OutlineServicesImplementation();
		context.registerService(OutlineServices.class, services, null);
	}

	public void notifySelectedNode(ASTNode node) {
		for(OutlineListener l : listeners)
			l.itemSelected(node);
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.context = null;
		
	}

	public BundleContext getContext() {
		return context;
	}
}
