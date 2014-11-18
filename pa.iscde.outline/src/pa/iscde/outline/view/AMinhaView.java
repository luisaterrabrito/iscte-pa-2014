package pa.iscde.outline.view;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import pt.iscte.pidesco.extensibility.PidescoView;

public class AMinhaView implements PidescoView{

	@Override
	public void createContents(Composite viewArea,Map<String, Image> imageMap) {
		
		Image image = imageMap.get("ada.jpg");
		Label lbl = new Label(viewArea, SWT.NONE);
		lbl.setText("Coisas");
		viewArea.setBackgroundImage(image);
		
	}

}
