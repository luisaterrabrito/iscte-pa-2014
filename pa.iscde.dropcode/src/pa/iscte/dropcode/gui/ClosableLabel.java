package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class ClosableLabel extends Composite {

	private Label button;
	public static Image image_down;
	public static Image image_up;
	private static final Color backgroundColor = new Color(null, 230, 230, 230);

	public ClosableLabel(Composite parent, int style, String text) {
		super(parent, style);

		setLayout(new RowLayout(SWT.HORIZONTAL));
		Label label = new Label(this, SWT.CENTER);
		label.setBackground(backgroundColor);
		setBackground(backgroundColor);
		label.setText(text);
		button = new Label(this, SWT.PUSH);
		button.setImage(image_up);
		button.setBackground(backgroundColor);
	}

	public void addMouseAdapter(final ClosableLabelEvent event) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				button.setImage(image_down);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				event.clicked();
			}

		});
		button.addListener(SWT.MouseExit, new Listener() {
			public void handleEvent(Event e) {
				button.setImage(image_up);
			}
		});
	}

	public interface ClosableLabelEvent {

		public void clicked();
	}

}
