package pa.iscde.umldiagram;

import org.eclipse.swt.graphics.Color;

public interface UmlTheme {
	/**
	 * 
	 * @param className - complete class name
	 * @return swt color
	 */
	public Color getColor(Class<?> classPath);
}
