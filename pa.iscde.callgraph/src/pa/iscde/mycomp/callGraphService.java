package pa.iscde.mycomp;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class callGraphService implements ServiceFactory<JavaEditorServices> {

	@Override
	public JavaEditorServices getService(Bundle arg0,
			ServiceRegistration<JavaEditorServices> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ungetService(Bundle arg0,
			ServiceRegistration<JavaEditorServices> arg1,
			JavaEditorServices arg2) {
		// TODO Auto-generated method stub

	}

}
