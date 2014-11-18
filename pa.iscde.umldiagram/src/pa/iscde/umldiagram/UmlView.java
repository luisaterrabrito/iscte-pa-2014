package pa.iscde.umldiagram;


import java.awt.Font;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.zest.core.widgets.Graph;

import pt.iscte.pidesco.extensibility.PidescoView;

/**
 * @author Nuno e Diogo
 */
public class UmlView implements PidescoView {
	private static UmlView umlView;
	private Graph graph;
	
	public UmlView() {
		umlView = this;
	}
	
	

	@Override
	public void createContents(Composite viewArea,
			Map<String, Image> imageMap) {
		Image img = imageMap.get("background.jpg");
		viewArea.setBackgroundImage(img);
		Label l = new Label(viewArea, SWT.NONE);
		l.setText("Diagrama UML: ");
		
	}

	public static UmlView getInstance() {
		return umlView;
	}

}
