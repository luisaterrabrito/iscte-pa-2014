package pa.iscde.configurator.model.interfaces;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class DependencyStyleDefault implements DependencyStyle {

	@Override
	public Color getNodeColor(String bundleId) {
		// TODO Auto-generated method stub
		
		Display display=Display.getCurrent();
		return new Color(display,255,0,0);
	}

	@Override
	public Color getDependencyColor(String extensionPointId) {
		// TODO Auto-generated method stub

		Display display=Display.getCurrent();
		return new Color(display,0,255,0);
	}

}
