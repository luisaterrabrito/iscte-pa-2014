package pa.iscde.formulas.activator;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * Class that starts the BundleActivator for the formulas.
 * @author Gon�alo Horta & Tiago Saraiva
 *
 */
public class FormulasActivator implements BundleActivator {

	
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		final JavaEditorServices javaeditor = context.getService(ref);
		javaeditor.addListener(new JavaEditorListener.Adapter(){
			@Override
			public void fileOpened(File file) {
				if(FormulasView.getInstance()!=null){
					FormulasView formulaView = FormulasView.getInstance();
					formulaView.setTarget(javaeditor,file);
				}
			}
			
			
			
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}

}
