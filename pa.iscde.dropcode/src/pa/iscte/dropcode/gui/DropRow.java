package pa.iscte.dropcode.gui;

import java.util.LinkedList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import pa.iscde.dropcode.DropCodeActivator;
import pa.iscde.dropcode.dropreflection.DropAble;
import pa.iscde.dropcode.dropreflection.DropField;
import pa.iscde.dropcode.dropreflection.DropMethod;
import pa.iscde.dropcode.dropreflection.DropModifier.DM_Others;
import pa.iscde.dropcode.dropreflection.DropModifier.DM_Visibility;
import pa.iscde.dropcode.dropreflection.DropType;
import pa.iscde.dropcode.services.DropButton;
import pa.iscte.dropcode.gui.ClosableLabel.ClosableLabelEvent;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DropRow extends Composite {

	private DropRow me;
	private ASTNode node;

	public DropRow(ASTNode node, Composite parent, int style, DropAble dropable,
			LinkedList<DropButton> dropbuttons) {
		super(parent, style);
		me = this;
		this.node = node;
		RowLayout layout = new RowLayout();
		setLayout(layout);
		if (dropable instanceof DropField) {
			addCombo_visibility_modifier(dropable);
			addCombo_other_modifiers(dropable);
			addCombo_type(dropable);
			addTextfield_name(dropable);
		} else if (dropable instanceof DropMethod) {
			addCombo_visibility_modifier(dropable);
			addCombo_other_modifiers(dropable);
			addTextfield_name(dropable);
		}
		addPluginButtons(dropbuttons);
	}

	private void addPluginButtons(LinkedList<DropButton> dropbuttons) {
		for (DropButton dropButton : dropbuttons) {
			final DropButton db = dropButton;
			Button b = new Button(this, SWT.None);
			b.setText(db.getText());
			b.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					db.clicked(node);
				}
			});
		}

	}

	private void addCombo_visibility_modifier(final DropAble dropable) {
		DM_Visibility[] vm = DM_Visibility.values();

		final CCombo combo = new CCombo(this, SWT.NONE);
		int select = 0;
		for (int i = 0; i != vm.length; i++) {
			combo.add(vm[i].name().toLowerCase());
			if (vm[i].equals(dropable.getVisibilityModifier()))
				select = i;
		}
		combo.select(select);
		combo.setEditable(false);
		
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				DropCodeView.getInstance().save();
				
				String item = combo.getItem(combo.getSelectionIndex());
				JavaEditorServices service = DropCodeActivator.getJavaEditor();
				
				int visibilityModifierLength = dropable.getVisibilityModifier().name().length();
				System.out.println("Visibiliy Modifier Lenght" + visibilityModifierLength);
				service.insertText(service.getOpenedFile(), item, node.getStartPosition(), visibilityModifierLength);

				DropCodeView.getInstance().save();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
	}

	private void addCombo_other_modifiers(final DropAble dropable) {

		for (DM_Others dropModifier : DM_Others.values()) {

			final DM_Others dm = dropModifier;
			if (dropable.isModifierPresent(dm)) {

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
		String type = ((DropField) dropable).getType();
		System.out.println(type);
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

	public void update() {
		// TODO 1 Prioridade - Update Droprow
	}
}
