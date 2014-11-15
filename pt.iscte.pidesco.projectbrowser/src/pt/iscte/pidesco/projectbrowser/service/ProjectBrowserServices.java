package pt.iscte.pidesco.projectbrowser.service;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;

/**
 * Service containing the operations offered by the Project Browser view.
 */
public interface ProjectBrowserServices {

	/**
	 * Id of the refresh tool
	 */
	public static final String REFRESH_TOOL_ID = "pt.iscte.pidesco.projectbrowser.refresh";
	
	/**
	 * Obtain the root of the tree.
	 * @return non-null reference
	 */
	PackageElement getRootPackage();

	/**
	 * Activate filter
	 * @param filterId (non-null) id of the filter to activate
	 */
	void activateFilter(String filterId);

	/**
	 * Deactivate filter
	 * @param filterId (non-null) id of the filter to deactivate
	 */
	void deactivateFilter(String filterId);

	/**
	 * Add a project browser listener. If listener already added, does nothing.
	 * @param listener (non-null) reference to the listener
	 */
	void addListener(ProjectBrowserListener listener);

	/**
	 * Remove a project browser listener. If listener does not exist, does nothing.
	 * @param listener (non-null) reference to the listener
	 */
	void removeListener(ProjectBrowserListener listener);

}
