package pa.iscde.umldiagram;


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
	public void createContents(Composite viewArea,
			Map<String, Image> imageMap) {
		Image img = imageMap.get("teste.jpg");
		viewArea.setBackgroundImage(img);
		Label l = new Label(viewArea, SWT.NONE);
		l.setText("ai");
		// TODO Auto-generated method stub
		
	}

}
