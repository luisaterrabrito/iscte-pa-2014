package pa.iscde.dropcode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import pa.iscde.dropcode.dropreflection.DropClass;
import pa.iscde.dropcode.dropreflection.DropField;
import pa.iscte.dropcode.gui.ClosableLabel;
import pa.iscte.dropcode.gui.DropRow;
import pt.iscte.pidesco.extensibility.PidescoView;

public class DropCodeView implements PidescoView {

	private static DropCodeView instance;

	public static DropCodeView getInstance() {
		return instance;
	}

	private ExpandBar bar;
	private Composite fields, constructors, methods;
	private ExpandItem fieldsBarItem;
	private DropClass dropClass;

	@Override
	public void createContents(Composite comp,
			java.util.Map<String, Image> images) {

		ClosableLabel.image_up = images.get("x.png");
		ClosableLabel.image_down = images.get("x2.png");

		instance = this;
		bar = new ExpandBar(comp, SWT.V_SCROLL);

		// compFields = new Composite(bar, SWT.NONE);
		// FillLayout fillLayout = new FillLayout();
		// fillLayout.type = SWT.VERTICAL;
		// compFields.setLayout(fillLayout);

		fields = new Composite(bar, SWT.NONE);
		fields.setLayout(new FillLayout(SWT.VERTICAL));
		fieldsBarItem = new ExpandItem(bar, SWT.NONE, 0);
		fieldsBarItem.setText("Fields");
		fieldsBarItem.setControl(fields);

		constructors = new Composite(bar, SWT.NONE);
		ExpandItem constructorsBarItem = new ExpandItem(bar, SWT.NONE, 1);
		constructorsBarItem.setText("Constructors");
		constructorsBarItem.setControl(constructors);

		methods = new Composite(bar, SWT.NONE);
		ExpandItem methodsBarItem = new ExpandItem(bar, SWT.NONE, 2);
		methodsBarItem.setText("Methods");
		methodsBarItem.setControl(methods);

		// c.setBackgroundImage(images.get("background.jpg"));
	}

	public void update() {
		System.out.println("DropCodeView.update()");
		dropClass = DropCodeActivator.getDropClass();
		clearFields();
		// clearConstructors();
		// clearMethods();
		if (dropClass != null) {
			updateFields();
			// updateConstructors();
			// updateMethods();
		}
		fields.layout();
		fieldsBarItem.setHeight(fields.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		// constructors.layout();
		// constructorsBarItem.setHeight(constructors.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		// methods.layout();
		// methods.setHeight(methods.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		bar.layout();

	}

	private void clearFields() {
		for (Control child : fields.getChildren()) {
			System.out.println("Disposing of " + child);
			child.dispose();
		}
	}

	private void updateFields() {

		for (DropField df : dropClass.getFields()) {
			new DropRow(fields, SWT.NONE, df);
		}

	}
}
