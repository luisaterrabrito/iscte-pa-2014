package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import pa.iscde.dropcode.dropreflection.DropAble;
import pa.iscde.dropcode.dropreflection.DropField;
import pa.iscde.dropcode.dropreflection.DropModifier;
import pa.iscte.dropcode.gui.ClosableLabel.ClosableLabelEvent;

public class DropRow extends Composite {

	private DropRow me;

	public DropRow(Composite parent, int style, DropAble dropable) {
		super(parent, style);
		me = this;
		if (dropable instanceof DropField) {
			addCombo_visibility_modifier(dropable);
			// addCombo_other_modifiers(dropable);
			// addCombo_type(dropable);
			// addTextfield_name(dropable);
		}
	}

	private void addCombo_visibility_modifier(DropAble dropable) {
		DropModifier[] vm = DropModifier.getVisibilityModifiers();

		CCombo combo = new CCombo(me, SWT.NONE);
		for (int i = 0; i != vm.length; i++) {
			combo.add(vm[i].name());
			if (vm[i].equals(dropable.getVisibilityModifier()))
				combo.select(i);
		}
		combo.setEditable(false);
	}

	private void addCombo_other_modifiers(final DropAble dropable) {

		for (DropModifier dropModifier : DropModifier.getOtherModifiers()) {

			final DropModifier dm = dropModifier;
			if (dropable.isModifierPresent(dm)) {
				final ClosableLabel cl = new ClosableLabel(this, SWT.NONE, dm
						+ "");

				cl.addMouseAdapter(new ClosableLabelEvent() {
					@Override
					public void clicked() {
						cl.dispose();
						dropable.removeModifier(dm);
					}
				});
			}

		}
	}

	private void addCombo_type(DropAble dropable) {
		// DropModifier[] vm = DropModifier.getVisibilityModifiers();
		//
		// CCombo combo = new CCombo(this, SWT.NONE);
		// for (int i = 0; i != visibilityModifiers.length; i++) {
		// combo.add(visibilityModifiers[i].name());
		// if (visibilityModifiers[i].equals(dropModifier))
		// combo.select(i);
		// }
		// combo.setEditable(false);
	}

	private void addTextfield_name(DropAble dropable) {
		// DropModifier[] vm = DropModifier.getVisibilityModifiers();
		//
		// CCombo combo = new CCombo(this, SWT.NONE);
		// for (int i = 0; i != visibilityModifiers.length; i++) {
		// combo.add(visibilityModifiers[i].name());
		// if (visibilityModifiers[i].equals(dropModifier))
		// combo.select(i);
		// }
		// combo.setEditable(false);
	}

	private void setContent() {
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

		Text t = new Text(this, SWT.SINGLE);
		t.setMessage("Name");
	}

	public void update() {
		// TODO UPDATE DROPROW
	}
}
