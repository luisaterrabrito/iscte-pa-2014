package pa.iscde.dropcode;

import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import pt.iscte.pidesco.extensibility.PidescoView;

public class DropCodeView implements PidescoView {

	int d;
	
	public static DropCodeView getInstance() {
		return null;
	}

	@Override
	public void createContents(Composite c, Map<String, Image> images) {

		// int halign = SWT.CENTER, valign = SWT.FILL, hspan = 5, vspan = 0;
		// boolean hexcess = true, vexcess = false;
		// GridData layout = new GridData(halign, valign, hexcess, vexcess,
		// hspan, vspan);
		c.setLayout(new GridLayout());

		c.setBackgroundImage(images.get("background.jpg"));

		Label label = new Label(c, SWT.SHADOW_OUT);
		label.setText("Esfera de Elites");
		label.setToolTipText("filletes");

		Button a = new Button(c, SWT.PUSH);
		a.setText("Cena?");
		a.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 1)
					JOptionPane.showMessageDialog(null, "Cena");
			}
		});

		Button b = new Button(c, SWT.CHECK);
		b.setText("És Elite");
	}

}
