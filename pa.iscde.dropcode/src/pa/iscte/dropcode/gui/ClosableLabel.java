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
		button = new Button(this, SWT.PUSH);
		new Label(this, SWT.NONE).setText(text);
	}

	public void addMouseAdapter(MouseListener listener) {
		button.addMouseListener(listener);
	}

}
