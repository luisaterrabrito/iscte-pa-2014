package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ClosableLabel extends Composite {

	private Label button;
	public static Image x_image;

	public ClosableLabel(Composite parent, int style, String text) {
		super(parent, style);

		// Group group = new Group(this, SWT.SHADOW_ETCHED_IN);
		// group.setText("Group Label");

		setLayout(new RowLayout(SWT.HORIZONTAL));

		new Label(this, SWT.CENTER).setText(text);
		button = new Label(this, SWT.PUSH);
		button.setImage(x_image);
	}

	public void addMouseAdapter(MouseListener listener) {
		button.addMouseListener(listener);
	}

}
