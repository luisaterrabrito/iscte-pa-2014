package pa.iscde.documentation.internal;

import pa.iscde.documentation.service.IDocumentationListener;
import pa.iscde.documentation.service.IDocumentationServices;

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
 