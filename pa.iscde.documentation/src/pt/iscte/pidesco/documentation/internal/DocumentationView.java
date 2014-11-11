package pt.iscte.pidesco.documentation.internal;

import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import pt.iscte.pidesco.extensibility.PidescoView;

public class DocumentationView implements PidescoView {

	public DocumentationView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		// TODO Auto-generated method stub
		Label l = new Label(viewArea, 0);
		l.setText("Hello World");
	}

}
