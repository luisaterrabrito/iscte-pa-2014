package pt.iscte.pidesco.javaeditor.internal;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;


public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		Bundle b = FrameworkUtil.getBundle(Startup.class);
		try {
			b.start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
	}

}
