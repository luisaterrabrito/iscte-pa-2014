/**
 * Interface used to extend pa.iscde.configurator.dependencystyle extension Point
 * @author Joao Diogo Medeiros & Luis Dias
 * 
 * all the methods of the class are called for each Node (that represents a component)
 */
package pa.iscde.configurator.model.interfaces;

import org.eclipse.swt.graphics.Color;

public interface DependencyStyle {
	/**
	 * Get the color of a given node
	 * @param a String with the id of the bundle(component) to paint
	 * @return Color of the given component
	 */
	Color getNodeColor(String bundleId);
	/**
	 * Get the color of the dependencies of a given extension point
	 * @param a String with the id of the extension point to paint
	 * @return Color to paint the dependencies
	 */
	Color getDependencyColor(String extensionPointId);
	
	/**
	 * Get the color of a given node when selected
	 * @param a String with the id of the bundle(component) to paint
	 * @return Color of the given component when selected
	 */
	Color getSelectedNodeColor(String bundleId);
	/**
	 * Get the name of the created style
	 * @return a String with the name of the style
	 */
	String getName();
}
