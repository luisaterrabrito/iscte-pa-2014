package pt.iscte.pidesco.extensibility;

import org.eclipse.swt.graphics.Image;

/**
 * Service containing operations offered by the Pidesco infrastructure.
 */
public interface PidescoServices {

	/**
	 * Name of the images folder from which plugin images will be indexed.
	 */
	public static final String IMAGES_FOLDER = "images";
	
	/**
	 * View id of a Pidesco view (common to all views, which will have a secondary id)
	 */
	public static final String VIEW_ID = "pt.iscte.pidesco.view";
	
	
	
	/**
	 * Opens Pidesco view.
	 * @param viewId view id
	 */
	void openView(String viewId);

	/**
	 * Obtains image from plugin, assuming that the image is contained in the "images" folder.
	 * @param pluginId plugin id
	 * @param fileName name of the image file
	 * @return the image, or null if cannot be found
	 */
	Image getImageFromPlugin(String pluginId, String fileName);
	
	/**
	 * 
	 * @param toolId tool id
	 */
	void runTool(String toolId, boolean activate);

}