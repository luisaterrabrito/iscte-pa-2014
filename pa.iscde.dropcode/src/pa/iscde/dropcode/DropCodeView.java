package pa.iscde.dropcode;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Text;

import pa.iscde.dropcode.dropreflection.DropClass;
import pa.iscte.dropcode.gui.ClosableLabel;
import pa.iscte.dropcode.gui.ClosableLabel.ClosableLabelEvent;
import pt.iscte.pidesco.extensibility.PidescoView;

public class DropCodeView implements PidescoView {

	private static DropCodeView instance;

	public static DropCodeView getInstance() {
		return instance;
	}

	private ExpandBar bar;
	private Composite compFields;
	private static Image IMAGE_UP;
	private static Image IMAGE_DOWN;

	@Override
	public void createContents(Composite comp, Map<String, Image> images) {

		IMAGE_UP = images.get("x.png");
		IMAGE_DOWN = images.get("x2.png");

		instance = this;
		bar = new ExpandBar(comp, SWT.V_SCROLL);

		compFields = new Composite(bar, SWT.NONE);
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		compFields.setLayout(fillLayout);

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
		// b.setText("�s Elite");

	}

	public void update() {

		DropClass dropClass = DropCodeActivator.getDropClass();

		// for (DropField df : dropClass.getFields()) {
		// new DropRow(compFields, SWT.NONE, df);
		// }

		createFieldContent();

		ExpandItem fields = new ExpandItem(bar, SWT.NONE, 0);
		fields.setText("Fields");
		fields.setHeight(compFields.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		fields.setControl(compFields);

		ExpandItem constructors = new ExpandItem(bar, SWT.NONE, 1);
		constructors.setText("Constructors");

		ExpandItem methods = new ExpandItem(bar, SWT.NONE, 2);
		methods.setText("Methods");

	}

	public void createFieldContent() {
		Composite compField1 = new Composite(compFields, SWT.NONE);
		RowLayout layout = new RowLayout();
		compField1.setLayout(layout);

		CCombo c = new CCombo(compField1, SWT.NONE);
		c.add("public");
		c.add("private");
		c.add("protected");
		c.add("none");
		c.select(0);
		c.setEditable(false);

		ClosableLabel.image_up = IMAGE_UP;
		ClosableLabel.image_down = IMAGE_DOWN;

		ClosableLabel cl = new ClosableLabel(compField1, SWT.NONE, "static");
		ClosableLabel cl2 = new ClosableLabel(compField1, SWT.NONE, "final");
		ClosableLabel cl3 = new ClosableLabel(compField1, SWT.NONE, "abstract");

		cl2.addMouseAdapter(new ClosableLabelEvent() {
			@Override
			public void clicked() {
				System.out.println("Clicked!");
			}
		});

		CCombo c2 = new CCombo(compField1, SWT.NONE);
		c2.add("int");
		c2.add("boolean");
		c2.add("char");
		c2.add("double");
		c2.add("String");
		c2.select(0);
		c2.setEditable(true);

		Text t = new Text(compField1, SWT.SINGLE);
		t.setMessage("Name");

	}

	public void clear() {

		for (Control c : compFields.getChildren()) {
			c.dispose();
		}

	}

}
