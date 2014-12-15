/******************************************************************************
 * Copyright (c) 2014, Samuel Jose Pinto
 * All right reserved. 
 * 
 * @author t-saribe
 * Created on Dez 01, 2014
 * 
 *****************************************************************************/

package pa.iscde.indent;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.indent.extensibility.ICodeIndenter;
import pa.iscde.indent.internal.CodeIndentJava;
import pa.iscde.indent.internal.EditorListenerAdapter;
import pa.iscde.indent.service.ICodeIndentListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * The Bundle activator.
 */
public class Activator implements BundleActivator {

	/**
	 * Singleton pattern private instance
	 */
	private static Activator instance;
	private BundleContext context;
	
	private Set<ICodeIndentListener> listeners;
	private Set<ICodeIndenter> indenters;
	
	private EditorListenerAdapter editorListener;
	private JavaEditorServices editorServices;

	/**
	 * The Activator default constructor.
	 */
	public Activator(){
		// Set instance
		instance = this;
		editorListener = new EditorListenerAdapter();
		listeners = new HashSet<ICodeIndentListener>();
		indenters = new HashSet<ICodeIndenter>();
	}
	
	public Set<ICodeIndentListener> getListeners(){
		return listeners;
	}
	
	public Set<ICodeIndenter> getIndenters(){
		return indenters;
	}
	
	/**
	 * Singleton pattern getter.
	 * @return Activator
	 */
	public static Activator getInstance() {
		return instance;
	}
	
	public File getOpenFile(){
		return editorServices.getOpenedFile();
	}
	public void addListener(ICodeIndentListener l) {
		listeners.add(l);
	}
	
	public void removeListener(ICodeIndentListener l) {
		listeners.remove(l);
	}
	
	public void addIndenter(ICodeIndenter l) {
		indenters.add(l);
	}
	
	public void removeIndenter(ICodeIndenter l) {
		indenters.remove(l);
	}

	/**
	 * Called when this bundle is started so the Framework can perform the bundle-specific activities necessary to start this bundle.
	 * This method can be used to register services or to allocate any resources that this bundle needs.
	 * This method must complete and return to its caller in a timely manner.
	 * @param context - The execution context of the bundle being started.
	 * @throws java.lang.Exception - If this method throws an exception, this bundle is marked as stopped and the Framework will remove this bundle's listeners, unregister all services registered by this bundle, and release all services used by this bundle.
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		
		// Set instance
		instance = this;
		// set context
		this.context = context;
		addIndenter(new CodeIndentJava());
		
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(ref);
		editorServices.addListener(editorListener);
	}

	/**
	 * Called when this bundle is stopped so the Framework can perform the bundle-specific activities necessary to stop the bundle.
	 * In general, this method should undo the work that the BundleActivator.start method started.
	 * There should be no active threads that were started by this bundle when this bundle returns.
	 * A stopped bundle must not call any Framework objects.
	 * This method must complete and return to its caller in a timely manner.
	 * @param context - The execution context of the bundle being stopped.
	 * @throws java.lang.Exception - If this method throws an exception, the bundle is still marked as stopped, and the Framework will remove the bundle's listeners, unregister all services registered by the bundle, and release all services used by the bundle.
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
	
		this.context = null;
		editorServices.removeListener(editorListener);
	}
	
	/**
	 * A bundle's execution context within the Framework. 
	 * The context is used to grant access to other methods so that this bundle can interact with the Framework.
	 * @return the actual Bundle Context
	 */
	public BundleContext getContext() {
		return context;
	}

}
