package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import pa.iscde.dropcode.dropreflection.DropAble;
import pa.iscde.dropcode.dropreflection.DropField;
import pa.iscde.dropcode.dropreflection.DropModifier.DM_Others;
import pa.iscde.dropcode.dropreflection.DropModifier.DM_Visibility;
import pa.iscde.dropcode.dropreflection.DropType;
import pa.iscte.dropcode.gui.ClosableLabel.ClosableLabelEvent;

public class DropRow extends Composite {

	private DropRow me;

	public DropRow(Composite parent, int style, DropAble dropable) {
		super(parent, style);
		me = this;
		RowLayout layout = new RowLayout();
		setLayout(layout);
		if (dropable instanceof DropField) {
			addCombo_visibility_modifier(dropable);
			addCombo_other_modifiers(dropable);
			addCombo_type(dropable);
			addTextfield_name(dropable);
		}
	}

	private void addCombo_visibility_modifier(DropAble dropable) {
		DM_Visibility[] vm = DM_Visibility.values();

		CCombo combo = new CCombo(this, SWT.NONE);
		int select = 0;
		for (int i = 0; i != vm.length; i++) {
			combo.add(vm[i].name().toLowerCase());
			if (vm[i].equals(dropable.getVisibilityModifier()))
				select = i;
		}
		combo.select(select);
		combo.setEditable(false);
	}

	private void addCombo_other_modifiers(final DropAble dropable) {

		for (DM_Others dropModifier : DM_Others.values()) {

			final DM_Others dm = dropModifier;
			if (true) { // TODO if (dropable.isModifierPresent(dm)) {

				final ClosableLabel cl = new ClosableLabel(this, SWT.NONE, dm
						.toString().toLowerCase());

				cl.addMouseAdapter(new ClosableLabelEvent() {
					@Override
					public void clicked() {
						cl.dispose();
						dropable.removeModifier(dm);
						me.layout();
					}
				});
			}
		}
	}

	private void addCombo_type(DropAble dropable) {
		DropType[] types = DropType.values();

		CCombo combo = new CCombo(this, SWT.NONE);
		for (int i = 0; i != types.length; i++) {
			combo.add(types[i].name().toLowerCase());
			// if (types[i].toString().toLowerCase().equals(dropable.getType()))
			// {
			// combo.select(i);
			// }
		}
		String type = dropable.getType();
		combo.setText(type == null ? "Object" : type);
		combo.setEditable(true);
	}

	private void addTextfield_name(DropAble dropable) {
		Text t = new Text(this, SWT.SINGLE);
		switch (dropable.getClass().getSimpleName()) {
		case "DropField":
			t.setMessage("<name of field>");
			break;
		case "DropMethod":
			t.setMessage("<name of method>");
			break;

		}

		t.setText(dropable.name());
	}

	private void setContent(String name) {

		if (name.equals("jButton1")) {
			ClosableLabel cl = new ClosableLabel(this, SWT.NONE, "static");
			ClosableLabel cl2 = new ClosableLabel(this, SWT.NONE, "final");
			ClosableLabel cl3 = new ClosableLabel(this, SWT.NONE, "abstract");
		} else if (name.equals("jTextField1")) {
			ClosableLabel cl2 = new ClosableLabel(this, SWT.NONE, "final");
		} else {
			ClosableLabel cl = new ClosableLabel(this, SWT.NONE, "static");
		}

	}

	public void update() {
		// TODO UPDATE DROPROW
	}
}
