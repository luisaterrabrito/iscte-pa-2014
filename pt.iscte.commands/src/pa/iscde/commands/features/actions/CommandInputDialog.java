package pa.iscde.commands.features.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pa.iscde.commands.controllers.KeyPressDetector;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.utils.Labels;

class CommandInputDialog extends Dialog {

	private Text keyInput;
	private Label inputLabel;

	private KeyPressListener edit;
	private CommandKey key;
	private String context;

	public CommandInputDialog(Shell parent, String context) {
		super(parent);
		this.context = context;
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
		fieldsLayout.heightHint = 15;
		inputLabel.setLayoutData(fieldsLayout);

		edit = new KeyPressListener(keyInput, context) {
			@Override
			public void keyPressed(CommandKey c) {
				super.keyPressed(c);

				if (isKeyOK) {
					inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);
				} else {
					inputLabel.setText(Labels.KEYALREADYUSE_LBL);
				}
			}
		};
		KeyPressDetector.getInstance().addKeyPressListener(edit);

		return container;
	}

	public CommandKey getKey() {
		return key;
	}

	@Override
	protected void okPressed() {
		key = edit.getKey();
		KeyPressDetector.getInstance().removeKeyPressListener(edit);
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		KeyPressDetector.getInstance().removeKeyPressListener(edit);
		key = null;
		super.cancelPressed();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.CHANGEKEYTITLE_LBL);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(320, 150);
	}

}
