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
	private MyView view;
	
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
	 * The starting point of CallGraph plugin.
	 * 
	 * @param context the ISCDE context in which CallGraph will be placed
	 * 
	 * @throws NÃO SEI!!!*********************************************************************
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		INSTANCE = this;
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServices = context.getService(ref);
		javaServices.addListener(new JavaEditorListener.Adapter() {
			@Override
			public void selectionChanged(File file, String text, int offset, int length){
				view = MyView.getInstance();
				view.newFile(file);
			}
		});
	}

	/**
	 * 
	 * The ending point of CallGraph plugin.
	 * 
	 * @param context the ISCDE context in which CallGraph is placed
	 * 
	 * @throws NÃO SEI!!!*********************************************************************
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
}