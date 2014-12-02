package pa.iscde.snippets.gui.dialogboxes;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pa.iscde.snippets.data.Variable;

public class ValueInsertionDialog extends TitleAreaDialog {

	private HashMap<String, Variable> variables;
	private String titleText;
	private String infoText;
	private SortedMap<String, Text> parametersValuesFromDialog = new TreeMap<String, Text>();

	public ValueInsertionDialog(Shell parentShell, String titleText,
			String infoText, HashMap<String, Variable> variables) {
		super(parentShell);
		this.variables = variables;
		this.titleText = titleText;
		this.infoText = infoText;
	}

	@Override
	public void create() {
		super.create();
		setTitle(titleText);
		setMessage(infoText, IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout(2, false);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		container.setLayout(layout);
		createParameterList(container);
		return area;
	}

	private void createParameterList(Composite container) {
		for (Variable variable : variables.values()) {
			Composite nameAndTypeContainer = new Composite(container, SWT.NONE);
			nameAndTypeContainer.setLayout(new GridLayout(2, false));
			if (variable.getType() != null) {
				Label parameterType = new Label(nameAndTypeContainer, SWT.NONE);
				parameterType.setText(variable.getType() + " ");
				parameterType.setForeground(new Color(Display.getCurrent(),
						112, 0, 112));
			}
			Label parameterName = new Label(nameAndTypeContainer, SWT.NONE);
			parameterName.setText(variable.getName() + ":");

			GridData dataParameter = new GridData();
			dataParameter.grabExcessHorizontalSpace = true;
			dataParameter.horizontalAlignment = GridData.FILL;

			Text parameterValueTextBox = new Text(container, SWT.BORDER);
			parameterValueTextBox.setLayoutData(dataParameter);

			parametersValuesFromDialog.put(variable.getName(),
					parameterValueTextBox);
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		for (String variableName : parametersValuesFromDialog.keySet()) {
			variables.get(variableName).setValue(
					parametersValuesFromDialog.get(variableName).getText());
		}
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}
}
