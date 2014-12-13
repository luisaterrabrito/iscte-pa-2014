package pa.iscde.callgraph;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * 
 * This is the activator class for the CallGraph ISCDE plugin
 * 
 * @author João Alves, Pedro Neves
 *
 */
public class Activator implements BundleActivator {

	private static Activator INSTANCE;
	private BundleContext context;
	private CallGraphView view;
	
	/**
	 * 
	 * Returns the instance of the CallGraph activator
	 * 
	 * @return the activator instance
	 */
	public static Activator getInstance(){
		return INSTANCE;
	}
	
	/**
	 * 
	 * Returns the associated BundleContext
	 * 
	 * @return the activator instance
	 */
	public BundleContext getContext(){
		return context;
	}
	
	/**
	 * 
	 * The starting point of CallGraph plugin.
	 * 
	 * @param context the ISCDE context in which CallGraph will be placed
	 * 
	 * @throws exception
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		INSTANCE = this;
		this.context = context;
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServices = context.getService(ref);
		javaServices.addListener(new JavaEditorListener.Adapter() {
			@Override
			public void selectionChanged(File file, String text, int offset, int length){
				view = CallGraphView.getInstance();
				view.newFile(file);
			}
			public void fileOpened(File file) {
				view = CallGraphView.getInstance();
				view.setCurrentFile(file);
			}
		});
		
	}

	/**
	 * 
	 * The ending point of CallGraph plugin.
	 * 
	 * @param context the ISCDE context in which CallGraph is placed
	 * 
	 * @throws exception
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
}