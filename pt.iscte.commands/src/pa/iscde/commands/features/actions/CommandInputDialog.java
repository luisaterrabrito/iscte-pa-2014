package pa.iscde.commands.features.actions;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pa.iscde.commands.controllers.KeyPressDetector;
import pa.iscde.commands.controllers.KeyPressDetector.KeyUpListener;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandKey;
import pa.iscde.commands.utils.Labels;

class CommandInputDialog extends Dialog {

	private Label keyInput;
	private Label inputLabel;

	private KeyPressListener edit;
	private CommandKey key;
	private String commandDefinition;
	private Composite parent;

	private boolean pressing;

	private KeyUpListener keyUpListener = new KeyUpListener() {

		@Override
		public boolean keyRelease(Event c) {
			if (pressing) {
				System.out.println("Largaste algo que nao tinha nada..");
				inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);
			}
			pressing = false;
			return true;
		}

	};

	final Thread changingColor = new Thread(new Runnable() {

		@Override
		public void run() {
			try {
				while (true) {

					parent.getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							keyInput.setForeground(new Color(null, 255, 0, 0));
						}
					});

					Thread.sleep(500);

					// O dialog pode. ser fechado logo a seguir da thread
					// acordar, lançando o interrupt
					// E assim controlamos que o display terá de estar vivo para
					// fazer o attach do runnable
					if (!Thread.interrupted()) {

						parent.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								keyInput.setForeground(new Color(null, 200,
										200, 200));
							}
						});

						Thread.sleep(500);
					}

				}
			} catch (InterruptedException e) {

			}

		}
	});

	public CommandInputDialog(Shell parent, String commandDefinition) {
		super(parent);
		this.commandDefinition = commandDefinition;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		this.parent = parent;

		parent.getShell().addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				if (changingColor.isAlive())
					changingColor.interrupt();
			}
		});

		GridData keyInputLayout = createGridData(15, 70);
		keyInput = new Label(container, SWT.NONE);
		keyInput.setText("...waiting...");
		keyInput.setFocus();
		keyInput.setLayoutData(keyInputLayout);

		changingColor.start();

		GridData fieldsLayout = createGridData(15, 215);
		inputLabel = new Label(container, SWT.NONE);
		inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);
		inputLabel.setLayoutData(fieldsLayout);

		GridData fieldsLayout2 = createGridData(15, 68);
		Label l = new Label(container, SWT.NONE);
		l.setText("CTRL + 'KEY'");
		l.setLayoutData(fieldsLayout2);

		GridData fieldsLayout3 = createGridData(15, 62);
		Label l2 = new Label(container, SWT.NONE);
		l2.setText("ALT + 'KEY'");
		l2.setLayoutData(fieldsLayout3);

		GridData fieldsLayout4 = createGridData(15, 105);
		Label l3 = new Label(container, SWT.NONE);
		l3.setText("CTRL + ALT + 'KEY'");
		l3.setLayoutData(fieldsLayout4);

		edit = new KeyPressListener(keyInput, commandDefinition) {
			@Override
			public boolean keyPressed(Event event) {
				boolean result = super.keyPressed(event);
				pressing = true;

				if (result) {
					inputLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);
					pressing = false;
					parent.getShell().setBackgroundMode(SWT.INHERIT_FORCE);
					keyInput.setBackground(null);
					System.out.println("ca dentro: " + key);
					return true;
				} else if (!defaultKeyPressed && !result) {
					inputLabel.setText(Labels.KEYALREADYUSE_LBL);
					pressing = false;
					return false;
				} else {
					parent.getShell().setBackgroundMode(SWT.INHERIT_FORCE);
					keyInput.setBackground(null);
					inputLabel.setText("Waiting..");
					return false;
				}

			}
		};
		KeyPressDetector.getInstance().addKeyPressListener(edit);
		KeyPressDetector.getInstance().addKeyReleaseListener(keyUpListener);

		return container;
	}

	public CommandKey getKey() {
		System.out.println("ca dentro M: " + key);
		return key;
	}

	private GridData createGridData(int x, int y) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.CENTER;
		gridData.grabExcessVerticalSpace = true;
		gridData.heightHint = x;
		gridData.widthHint = y;
		return gridData;
	}

	@Override
	protected void okPressed() {
		changingColor.interrupt();
		key = edit.getKey();
		KeyPressDetector.getInstance().removeKeyPressListener(edit);
		KeyPressDetector.getInstance().removeKeyReleaseListener(keyUpListener);
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		changingColor.interrupt();
		KeyPressDetector.getInstance().removeKeyPressListener(edit);
		KeyPressDetector.getInstance().removeKeyReleaseListener(keyUpListener);
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
		return new Point(320, 300);
	}

}
