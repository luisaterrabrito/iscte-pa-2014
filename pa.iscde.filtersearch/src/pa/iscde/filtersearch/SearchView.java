package pa.iscde.filtersearch;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;

public class SearchView implements PidescoView {

	private static Composite viewArea;
	
	public SearchView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		SearchView.viewArea = viewArea;
		Button b = new Button(null, SWT.PUSH);
		b.setText("TESTE");
		b.setBounds(10, 10, 50, 20);
	}

}
