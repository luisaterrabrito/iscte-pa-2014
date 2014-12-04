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
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.utils.Labels;

public class CommandInputDialog extends Dialog {

	private Text keyInput;
	private Label inputLabel;

	private KeyPressEdit edit;
	private CommandKey key;
	private CommandDefinition commandDefinition;

	public CommandInputDialog(Shell parent, CommandDefinition commandDefinition) {
		super(parent);
		this.commandDefinition = commandDefinition;

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridData fieldsLayout = new GridData();
		fieldsLayout.horizontalAlignment = SWT.FILL;
		fieldsLayout.grabExcessHorizontalSpace = true;
		fieldsLayout.verticalAlignment = SWT.TOP;
		fieldsLayout.grabExcessVerticalSpace = false;

		keyInput = new Text(container, SWT.BORDER);
		keyInput.setEditable(false);
		keyInput.setBackground(new Color(null, 255, 255, 255));
		keyInput.setFocus();
		inputLabel = new Label(container, SWT.NONE);
		inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);

		keyInput.setLayoutData(fieldsLayout);
		fieldsLayout.heightHint = 30;
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
		key = null;
		super.cancelPressed();
	}

	private final class KeyPressEdit implements KeyStrokeListener {
		@Override
		public void keyPressed(CommandKey c) {
			keyInput.setText(c.toString());

			boolean result = true;
			for (CommandDefinition it : CommandWarehouse
					.getCommandByContext(commandDefinition.getContext())) {
				if (it.getCommandKey().keyEquals(c)) {
					result = false;
				}
			}

			if (result) {
				keyInput.setBackground(new Color(null, 255, 255, 255));
				key = c;
				inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);
			} else {
				keyInput.setBackground(new Color(null, 255, 0, 0));
				key = null;
				inputLabel.setText(Labels.KEYALREADYUSE_LBL);
			}

		}
	}

}
