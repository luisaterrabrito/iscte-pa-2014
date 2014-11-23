package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class ClosableLabel extends Composite {

	private Label button;

	public ClosableLabel(Composite parent, int style, String text) {
		super(parent, style);

		Group group = new Group(this, SWT.NONE);
		group.setLayout(new RowLayout(SWT.HORIZONTAL));

		new Label(group, SWT.CENTER).setText(text);
		button = new Label(group, SWT.PUSH);
		button.setText("X");
	}

	public void addMouseAdapter(MouseListener listener) {
		button.addMouseListener(listener);
	}

}
