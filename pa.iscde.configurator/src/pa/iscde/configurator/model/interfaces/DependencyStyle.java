package pa.iscde.configurator.model.interfaces;

import org.eclipse.swt.graphics.Color;
import org.osgi.framework.Bundle;

public interface DependencyStyle {
	Color getNodeColor(String bundleId);
	Color getDependencyColor(String extensionPointId);
}
