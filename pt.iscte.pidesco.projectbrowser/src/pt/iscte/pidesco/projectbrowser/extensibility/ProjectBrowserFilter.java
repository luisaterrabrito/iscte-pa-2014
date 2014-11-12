package pt.iscte.pidesco.projectbrowser.extensibility;


import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * Represents a filter that can be activated/deactivated in the Project Browser.
 */
public interface ProjectBrowserFilter {
	
	/**
	 * Include the element?
	 * @param element Element to include or not
	 * @param parent Parent package of the element
	 * @return true if the element should be included, false otherwise
	 */
	boolean include(SourceElement element, PackageElement parent);
}
