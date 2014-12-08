package pa.iscde.commands.features.actions;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import pa.iscde.commands.controllers.KeyPressDetector;
import pa.iscde.commands.external.services.CommandViewTree;
import pa.iscde.commands.utils.Labels;

public class NewCommandDialog extends Dialog {

	private List list;
	private Button rightToLeft;
	private Button leftToRight;

	private Text inputKey;

	private CommandViewTree commandsAndViews;
	private KeyPressListener keyPressListener;
	private Label inputKeyLabel;

	protected NewCommandDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		inputKeyLabel = new Label(container, SWT.NONE);
		inputKeyLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);

		inputKey = new Text(container, SWT.BORDER);
		inputKey.setEditable(false);
		inputKey.setBackground(new Color(null, 255, 255, 255));
		inputKey.setFocus();
		inputKey.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

		keyPressListener = new KeyPressListener(inputKey) {
			@Override
			public boolean keyPressed(Event c) {
				if(super.keyPressed(c)){
					inputKeyLabel.setText(Labels.CLICKKEYCOMBINATIONS_LBL);
					return true;
				} else {
					inputKeyLabel.setText(Labels.KEYALREADYUSE_LBL);
					return false;
				}
			}
		};
		KeyPressDetector.getInstance().addKeyPressListener(keyPressListener);

		addViewsAndCommands(container);

		populateAllCommands();

		return container;
	}

	private void populateAllCommands() {
		for (int i = 0; i < 100; i++) {
			list.add("OLAA " + i);
			list.setData("OLAA " + i, null);
		}
	}

	private void addViewsAndCommands(Composite container) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		new Label(container, SWT.NONE).setText(Labels.VIEWSANDCOMMANDS_LBL);

		Composite area = new Composite(container, SWT.NONE);
		area.setLayoutData(gridData);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		area.setLayout(gridLayout);

		Composite commandsAndViewsComposite = new Composite(area, SWT.BORDER);
		commandsAndViews = new CommandViewTree(commandsAndViewsComposite);

		Composite btns = new Composite(area, SWT.BORDER);
		btns.setLayout(new FillLayout(SWT.VERTICAL));
		rightToLeft = new Button(btns, SWT.NONE);
		rightToLeft.setText("<");
		leftToRight = new Button(btns, SWT.NONE);
		leftToRight.setText(">");

		list = new List(area, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		list.setLayoutData(gridData);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.VIEWSANDCOMMANDS_LBL);
	}

	@Override
	protected void cancelPressed() {
		KeyPressDetector.getInstance().removeKeyPressListener(keyPressListener);
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		KeyPressDetector.getInstance().removeKeyPressListener(keyPressListener);
		// TODO save keys in model
		super.okPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(800, 500);
	}

}
