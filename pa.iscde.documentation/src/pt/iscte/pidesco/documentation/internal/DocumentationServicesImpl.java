package pt.iscte.pidesco.documentation.internal;

import pt.iscte.pidesco.documentation.service.IDocumentationListener;
import pt.iscte.pidesco.documentation.service.IDocumentationServices;

public class DocumentationServicesImpl implements IDocumentationServices {

	@Override
	public void addListener(IDocumentationListener listener) {
		Activator.getInstance().addListener(listener);
	}

	@Override
	public void removeListener(IDocumentationListener listener) {
		Activator.getInstance().removeListener(listener);
	}

}
