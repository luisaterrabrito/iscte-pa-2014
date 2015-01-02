package pa.iscde.documentation.internal;

import pa.iscde.documentation.service.IDocumentationListener;
import pa.iscde.documentation.service.IDocumentationServices;

/**
 * This is the services implementation for the Documentation ISCDE plugin
 * 
 * @author David Franco & João Gonçalves
 * @version 01.00
 */
public class DocumentationServicesImpl implements IDocumentationServices {

	/**
	 * Adding a listener to the Documentation View
	 * 
	 * @param listener
	 */
	@Override
	public void addListener(IDocumentationListener listener) {
		Activator.getInstance().addListener(listener);
	}

	/**
	 * Removing a listener to the Documentation View
	 * 
	 * @param listener
	 */
	@Override
	public void removeListener(IDocumentationListener listener) {
		Activator.getInstance().removeListener(listener);
	}

}
 