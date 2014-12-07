package pa.iscde.commands.features.actions;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;

import pa.iscde.commands.controllers.KeyPressDetector.KeyStrokeListener;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.models.CommandWarehouse;

class KeyPressListener implements KeyStrokeListener {

	private Text keyInput;
	private CommandKey key;
	private String context;
	protected boolean isKeyOK;

	public KeyPressListener(Text keyInput) {
		this.keyInput = keyInput;
		context = "";
	}

	public KeyPressListener(Text keyInput, String context) {
		this.keyInput = keyInput;
		this.context = context;
	}

	public CommandKey getKey() {
		return key;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public void keyPressed(CommandKey c) {
		keyInput.setText(c.toString());

		isKeyOK = true;
		for (CommandDefinition it : CommandWarehouse
				.getCommandByContext(context)) {
			if (it.getCommandKey().keyEquals(c)) {
				isKeyOK = false;
			}
		}

		if (isKeyOK) {
			keyInput.setBackground(new Color(null, 255, 255, 255));
			key = c;
		} else {
			keyInput.setBackground(new Color(null, 255, 0, 0));
			key = null;
		}

	}
}
