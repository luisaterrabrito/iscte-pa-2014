package pa.iscde.documentation.service;

/**
 * This is the services interface for the Documentation ISCDE plugin
 * 
 * @author David Franco & João Gonçalves
 * @version 01.00
 */
public interface IDocumentationServices {

	/**
	 * Adding a listener to the Documentation View
	 * 
	 * @param listener
	 */
	public void addListener(IDocumentationListener listener);

	/**
	 * Removing a listener to the Documentation View
	 * 
	 * @param listener
	 */
	public void removeListener(IDocumentationListener listener);

}
