package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ClosableLabel extends Composite {

	private Button button;

	public ClosableLabel(Composite parent, int style, String text) {
		super(parent, style);

		setLayout(new RowLayout(SWT.HORIZONTAL));
		
		new Label(this, SWT.CENTER).setText(text);
		button = new Button(this, SWT.PUSH);
		button.setText("X");
	}

	public void addMouseAdapter(MouseListener listener) {
		button.addMouseListener(listener);
	}

}
