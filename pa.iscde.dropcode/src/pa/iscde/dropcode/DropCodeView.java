package pa.iscde.dropcode;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import pa.iscde.dropcode.dropreflection.DropClass;
import pa.iscde.dropcode.dropreflection.DropField;
import pa.iscte.dropcode.gui.DropRow;
import pt.iscte.pidesco.extensibility.PidescoView;

public class DropCodeView implements PidescoView {

	private static DropCodeView instance;

	public static DropCodeView getInstance() {
		return instance;
	}

	@Override
	public void createContents(Composite comp, Map<String, Image> images) {

		instance = this;

		// int halign = SWT.CENTER, valign = SWT.FILL, hspan = 5, vspan = 0;
		// boolean hexcess = true, vexcess = false;
		// GridData layout = new GridData(halign, valign, hexcess, vexcess,
		// hspan, vspan);
		// c.setLayout(new GridLayout());
		//
		// c.setBackgroundImage(images.get("background.jpg"));
		//
		// Label label = new Label(c, SWT.SHADOW_OUT);
		// label.setText("Esfera de Elites");
		// label.setToolTipText("filletes");
		//
		// Button a = new Button(c, SWT.PUSH);
		// a.setText("Cena?");
		// a.addMouseListener(new MouseAdapter() {
		//
		// @Override
		// public void mouseDown(MouseEvent e) {
		// if (e.button == 1)
		// JOptionPane.showMessageDialog(null, "Cena");
		// }
		// });
		//
		// Button b = new Button(c, SWT.CHECK);
		// b.setText("És Elite");

		DropClass dClass = DropCodeActivator.getDropClass();

		ExpandBar bar = new ExpandBar(comp, SWT.V_SCROLL);

		Composite compFields = new Composite(bar, SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		compFields.setLayout(fillLayout);

		for (DropField df : dClass.getFields()) {
			new DropRow(compFields, SWT.NONE, df);
		}

		ExpandItem fields = new ExpandItem(bar, SWT.NONE, 0);
		fields.setText("Fields");
		fields.setHeight(compFields.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		fields.setControl(compFields);

		ExpandItem constructors = new ExpandItem(bar, SWT.NONE, 1);
		constructors.setText("Constructors");

		for (int c = 0; c != 5; c++) {

		}

		ExpandItem methods = new ExpandItem(bar, SWT.NONE, 2);
		methods.setText("Methods");

		for (int m = 0; m != 5; m++) {

		}

	}

}
