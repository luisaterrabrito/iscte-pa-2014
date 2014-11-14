package pa.iscde.snippets;

import java.util.Map;







import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pt.iscte.pidesco.extensibility.PidescoView;

public class PidescoView1 implements PidescoView {

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
//		Label label = new Label(viewArea, SWT.NONE);
//		label.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
//				false));
//		label.setText("yah va fica");
		
		new SnippetsExplorer(viewArea, SWT.NONE);

	}

}
