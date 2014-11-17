package pa.iscde.formulas.activator;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class FormulasActivator implements BundleActivator {

	
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		final JavaEditorServices javaeditor = context.getService(ref);
		javaeditor.addListener(new JavaEditorListener.Adapter(){
			@Override
			public void fileOpened(File file) {
				System.out.println("FILE OPEN!");
				FormulasView.getInstance();
				FormulasView.setTarget(javaeditor,file);
			}
			
			
			
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}

}
