package pt.iscte.pidesco.extensibility;

import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * Represents a Pidesco view.
 * Images that are contained in a folder named "images" located at the root of the plugin can accessed with
 * a key equal to the filename (e.g., "icon.png").
 * - There must exist a zero-argument constructor
 */
public interface PidescoView {
	
	/**
	 * @param viewArea (not-null) composite where the contents of the view should be added (by default, it is configured with the FillLayout)
	 * @paran imageMap (not-null) map indexing the images contained in the "images" folder of the plugin
	 */
	void createContents(Composite viewArea, Map<String, Image> imageMap);
}
