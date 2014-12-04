package pa.iscde.commands.features.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pa.iscde.commands.controllers.KeyPressDetector;
import pa.iscde.commands.controllers.KeyPressDetector.KeyStrokeListener;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.utils.Labels;

public class CommandInputDialog extends Dialog {

	private Text keyInput;
	private Label inputLabel;

	private KeyPressEdit edit;
	private CommandKey key;

	public CommandInputDialog(Shell parent) {
		super(parent);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridData fieldsLayout = new GridData();
		fieldsLayout.horizontalAlignment = SWT.FILL;
		fieldsLayout.heightHint = 19;

		keyInput = new Text(container, SWT.BORDER);
		keyInput.setEditable(false);
		keyInput.setBackground(new Color(null, 255, 255, 255));
		keyInput.setFocus();
		inputLabel = new Label(container, SWT.NONE);
		inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);

		keyInput.setLayoutData(fieldsLayout);
		inputLabel.setLayoutData(fieldsLayout);

		edit = new KeyPressEdit();
		KeyPressDetector.getInstance().addKeyPressListener(edit);

		return container;
	}

	public CommandKey getKey() {
		return key;
	}

	@Override
	protected void okPressed() {
		KeyPressDetector.getInstance().removeKeyPressListener(edit);
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		KeyPressDetector.getInstance().removeKeyPressListener(edit);
		super.cancelPressed();
	}

	private final class KeyPressEdit implements KeyStrokeListener {
		@Override
		public void keyPressed(CommandKey c) {
			keyInput.setText(c.toString());

			if (CommandWarehouse.containsKey(c)) {
				keyInput.setBackground(new Color(null, 255, 0, 0));
				key = null;
			} else {
				keyInput.setBackground(new Color(null, 255, 255, 255));
				key = c;
			}
		}
	}

}
