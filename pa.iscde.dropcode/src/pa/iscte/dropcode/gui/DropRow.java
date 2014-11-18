package pa.iscte.dropcode.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;

import pa.iscde.dropcode.dropreflection.DropAble;
import pa.iscde.dropcode.dropreflection.DropModifier;

public class DropRow extends Composite {

	public DropRow(Composite parent, int style, DropAble dropable) {
		super(parent, style);
		addCombo(DropModifier.getVisibilityModifiers(), dropable.getModifier());
		setName(dropable.name());
		// TODO: Add Modifier addButton();
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
