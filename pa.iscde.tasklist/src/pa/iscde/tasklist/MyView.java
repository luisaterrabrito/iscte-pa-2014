package pa.iscde.tasklist;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import pt.iscte.pidesco.extensibility.PidescoView;


public class MyView implements PidescoView {

	public MyView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		Label label = new Label(viewArea, SWT.NONE);
		label.setText("hello");
		
	}

}
