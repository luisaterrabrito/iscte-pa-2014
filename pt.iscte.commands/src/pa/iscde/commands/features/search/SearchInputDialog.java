package pa.iscde.commands.features.search;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pa.iscde.commands.features.search.SearchWord.DialogInputData;
import pa.iscde.commands.utils.Labels;

final class SearchInputDialog extends Dialog {

	private Text textInput;
	private Label inputLabel;
	private Button checkBox;
	private DialogInputData data;

	public SearchInputDialog(Shell parent, DialogInputData data) {
		super(parent);
		this.data = data;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridData fieldsLayout = new GridData();
		fieldsLayout.horizontalAlignment = SWT.FILL;
		fieldsLayout.grabExcessHorizontalSpace = true;
		fieldsLayout.verticalAlignment = SWT.TOP;
		fieldsLayout.grabExcessVerticalSpace = false;
		fieldsLayout.heightHint = 15;

		inputLabel = new Label(container, SWT.NONE);
		inputLabel.setText(Labels.SEARCHTEXT_LBL);
		inputLabel.setLayoutData(fieldsLayout);

		textInput = new Text(container, SWT.BORDER);
		textInput.setFocus();
		textInput.setLayoutData(fieldsLayout);

		checkBox = new Button(container, SWT.CHECK);
		checkBox.setText(Labels.CASESENSITIVE_LBL);

		return container;
	}

	@Override
	protected void okPressed() {
		data.setText(textInput.getText());
		data.setCaseSensitive(checkBox.isEnabled());
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.SEARCHINPUTTITLE_LBL);
	}

}
