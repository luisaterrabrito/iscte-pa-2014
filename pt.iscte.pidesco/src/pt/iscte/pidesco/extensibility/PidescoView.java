package pt.iscte.pidesco.extensibility;

import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public interface PidescoView {

	void createContents(Composite viewArea, Map<String, Image> imageMap);
	
}
