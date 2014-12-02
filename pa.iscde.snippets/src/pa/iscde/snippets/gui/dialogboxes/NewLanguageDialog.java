package pa.iscde.snippets.gui.dialogboxes;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewLanguageDialog extends TitleAreaDialog {

	private String titleText;
	private String infoText;
	private String labelText;
	private Text languageNameText;
	private String languageName;

	public NewLanguageDialog(Shell parentShell, String titleText,
			String infoText, String labelText) {
		super(parentShell);
		this.titleText = titleText;
		this.infoText = infoText;
		this.labelText = labelText;

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
		createLanguage(container);
		return area;
	}

	private void createLanguage(Composite container) {
		Label languageName = new Label(container, SWT.NONE);
		languageName.setText(labelText);

		GridData dataLanguage = new GridData();
		dataLanguage.grabExcessHorizontalSpace = true;
		dataLanguage.horizontalAlignment = GridData.FILL;

		languageNameText = new Text(container, SWT.BORDER);
		languageNameText.setLayoutData(dataLanguage);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	private void saveInput() {
		languageName = languageNameText.getText();
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getLanguageName() {
		return languageName;
	}
}
