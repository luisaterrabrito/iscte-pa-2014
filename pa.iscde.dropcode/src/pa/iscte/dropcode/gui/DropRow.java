package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import pa.iscde.dropcode.dropreflection.DropAble;
import pa.iscde.dropcode.dropreflection.DropModifier;
import pa.iscte.dropcode.gui.ClosableLabel.ClosableLabelEvent;

public class DropRow extends Composite {

	public DropRow(Composite parent, int style, DropAble dropable) {
		super(parent, style);
		// addCombo(DropModifier.getVisibilityModifiers(),
		// dropable.getVisibilityModifier());
		// setName(dropable.name());
		// TODO: Add Modifier addButton();

		RowLayout layout = new RowLayout();
		setLayout(layout);

		CCombo c = new CCombo(this, SWT.NONE);
		c.add("public");
		c.add("private");
		c.add("protected");
		c.add("none");
		c.select(0);
		c.setEditable(false);

		ClosableLabel cl = new ClosableLabel(this, SWT.NONE, "static");
		ClosableLabel cl2 = new ClosableLabel(this, SWT.NONE, "final");
		ClosableLabel cl3 = new ClosableLabel(this, SWT.NONE, "abstract");

		cl2.addMouseAdapter(new ClosableLabelEvent() {
			@Override
			public void clicked() {
				System.out.println("Clicked!");
			}
		});

		CCombo c2 = new CCombo(this, SWT.NONE);
		c2.add("int");
		c2.add("boolean");
		c2.add("char");
		c2.add("double");
		c2.add("String");
		c2.select(0);
		c2.setEditable(true);

		Text t = new Text(this, SWT.SINGLE);
		t.setMessage("Name");
	}

	private void addCombo(DropModifier[] visibilityModifiers,
			DropModifier dropModifier) {
		CCombo combo = new CCombo(this, SWT.NONE);

		for (int i = 0; i != visibilityModifiers.length; i++) {
			combo.add(visibilityModifiers[i].name());
			if (visibilityModifiers[i].equals(dropModifier))
				combo.select(i);
		}
	}

	private void setName(String text) {
		
	}

	public void addButton() {

	}

	public void update() {

	}
}
