package pt.iscte.pidesco.documentation.internal;

import pt.iscte.pidesco.documentation.service.IDocumentationListener;
import pt.iscte.pidesco.documentation.service.IDocumentationServices;

public class DocumentationServicesImpl implements IDocumentationServices {

	@Override
	public void addListener(IDocumentationListener listener) {
		DocumentationActivator.getInstance().addListener(listener);
	}

	@Override
	public void removeListener(IDocumentationListener listener) {
		DocumentationActivator.getInstance().removeListener(listener);
	}

}
