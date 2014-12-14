package pa.iscde.commands.features.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pa.iscde.commands.controllers.CommandKeyListener;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.services.CommandDefinition;
import pa.iscde.commands.services.CommandKey;

class KeyPressListener extends CommandKeyListener {

	private Text keyInputText;
	private Label keyInputLabel;
	private CommandKey key;
	private String context;
	protected boolean defaultKeyPressed;

	public KeyPressListener(Label keyInput) {
		this.keyInputLabel = keyInput;
		context = "";
	}

	public KeyPressListener(Text keyInput) {
		this.keyInputText = keyInput;
		context = "";
	}

	public KeyPressListener(Label keyInput, String context) {
		this.keyInputLabel = keyInput;
		this.context = context;
	}

	public CommandKey getKey() {
		return key;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public boolean keyPressed(Event event) {
		boolean commandKeyDetected = super.keyPressed(event);

		defaultKeyPressed = true;
		if (commandKeyDetected) {
			defaultKeyPressed = false;

			boolean ctrl_clicked = (event.stateMask & SWT.CTRL) == SWT.CTRL;
			boolean alt_clicked = (event.stateMask & SWT.ALT) == SWT.ALT;
			int keyCode_lastKey = event.keyCode;

			CommandKey typed = new CommandKey(context, ctrl_clicked,
					alt_clicked, (char) keyCode_lastKey);

			for (CommandDefinition it : CommandWarehouse.getInstance()
					.getCommandByContext(context)) {
				if (it.getCommandKey().keyEquals(typed)) {
					if (keyInputText != null)
						keyInputText.setBackground(new Color(null, 255, 0, 0));
					else
						keyInputLabel.setBackground(new Color(null, 255, 0, 0));

					key = null;
					return false;
				}
			}

			if (keyInputText != null) {
				keyInputText.setBackground(new Color(null, 0, 0, 0));
				keyInputText.setText(typed.toString());
			} else {
				keyInputLabel.setForeground(new Color(null, 0, 0, 0));
				keyInputLabel.setText(typed.toString());
			}

			key = typed;
			return true;
		}

		return false;

	}

}
