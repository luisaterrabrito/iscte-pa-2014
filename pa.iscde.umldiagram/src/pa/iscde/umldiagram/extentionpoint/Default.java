package pa.iscde.umldiagram.extentionpoint;

import org.eclipse.swt.graphics.Color;

import pa.iscde.umldiagram.UmlTheme;

public class Default implements UmlTheme{

	@Override
	public Color getColor(Class<?> className) {
		Color c = new Color(null, 0, 0, 255);
		return c;
	}
}
