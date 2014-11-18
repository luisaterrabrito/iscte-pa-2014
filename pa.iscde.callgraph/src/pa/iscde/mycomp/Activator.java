package pa.iscde.mycomp;

import javax.swing.JOptionPane;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		context.registerService(JavaEditorServices.class, new callGraphService(), null);
		JOptionPane.showConfirmDialog(null, "iu3hiog3h");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}