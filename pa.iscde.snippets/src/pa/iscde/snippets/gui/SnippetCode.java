package pa.iscde.snippets.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import pa.iscde.snippets.data.Variable;
import pa.iscde.snippets.fileoperations.FileOperations;
import pa.iscde.snippets.gui.dialogboxes.NewLanguageDialog;
import pa.iscde.snippets.gui.dialogboxes.ValueInsertionDialog;

public class SnippetCode extends Composite {
	private Text snippetNameTextBox;
	private Text snippetCodeText;
	private FileOperations fileOperations;
	private Combo languagesCombo;
	private ArrayList<String> languages;
	private Button editButton;
	private final Composite viewArea;

	public SnippetCode(File f, Composite viewArea, int style) {
		super(viewArea, style);
		this.viewArea = viewArea;
		fileOperations = new FileOperations(f);
		createContents();
		setSnippetTextAndName();
		setSelectedFileLanguage();
		snippetNameTextBox.setEditable(false);
		snippetCodeText.setEditable(false);
		languagesCombo.setEnabled(false);
		/*
		 * Uncomment if Rui wins argument snippetCodeText.setBackground(new
		 * Color(Display.getCurrent(), 255, 255, 255));
		 * snippetNameTextBox.setBackground(new Color(Display.getCurrent(), 255,
		 * 255, 255));
		 */
	}

	public SnippetCode(Composite viewArea, int style) {
		super(viewArea, style);
		this.viewArea = viewArea;
		createContents();
		editButton.setSelection(true);
		selectDefaultLanguage();
		fileOperations = new FileOperations();
	}

	public void createContents() {
		this.setLayout(new GridLayout(1, false));
		Composite nameAndEditAndLanguageSelectComposite = new Composite(this,
				SWT.None);
		GridLayout gl_nameAndEditAndLanguageSelectComposite = new GridLayout(4,
				false);
		gl_nameAndEditAndLanguageSelectComposite.marginWidth = 0;
		nameAndEditAndLanguageSelectComposite
				.setLayout(gl_nameAndEditAndLanguageSelectComposite);
		GridData gd_nameAndEditAndLanguageSelectComposite = new GridData(
				GridData.FILL_HORIZONTAL);
		gd_nameAndEditAndLanguageSelectComposite.verticalAlignment = SWT.FILL;
		nameAndEditAndLanguageSelectComposite
				.setLayoutData(gd_nameAndEditAndLanguageSelectComposite);

		Composite snippetNameComposite = new Composite(
				nameAndEditAndLanguageSelectComposite, SWT.None);
		snippetNameComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		snippetNameComposite.setLayout(null);

		// Snippet Name Label
		Label snippetNameTextLabel = new Label(snippetNameComposite, SWT.None);
		snippetNameTextLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		snippetNameTextLabel.setBounds(0, 3, 102, 20);
		snippetNameTextLabel.setText("Snippet Name: ");

		// Snippet Name Text Box
		snippetNameTextBox = new Text(snippetNameComposite, SWT.BORDER);
		snippetNameTextBox.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		snippetNameTextBox.setText("New Snippet (1)");
		snippetNameTextBox.setBounds(103, 1, 126, 25);

		Composite composite = new Composite(
				nameAndEditAndLanguageSelectComposite, SWT.NONE);
		composite.setLayout(null);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, true,
				1, 1);
		gd_composite.heightHint = 33;
		composite.setLayoutData(gd_composite);
		Label languagesLabel = new Label(composite, SWT.None);
		languagesLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		languagesLabel.setBounds(0, 3, 70, 19);
		languagesLabel.setText("Language: ");
		languagesCombo = new Combo(composite, SWT.FILL | SWT.READ_ONLY);
		languagesCombo.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		languagesCombo.setBounds(73, 0, 185, 28);
		setLanguagesBox(this.viewArea);
		new Label(nameAndEditAndLanguageSelectComposite, SWT.NONE);
		editButton = new Button(nameAndEditAndLanguageSelectComposite,
				SWT.CHECK);
		editButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true,
				1, 1));
		editButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		editButton.setText("Edit");
		editButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (editButton.getSelection()) {
					snippetNameTextBox.setEditable(true);
					snippetCodeText.setEditable(true);
					languagesCombo.setEnabled(true);
				} else {
					snippetNameTextBox.setEditable(false);
					/*
					 * Uncomment if Rui wins argument
					 * snippetNameTextBox.setBackground(new Color(Display
					 * .getCurrent(), 255, 255, 255));
					 */
					snippetCodeText.setEditable(false);
					/*
					 * Uncomment if Rui wins argument
					 * snippetCodeText.setBackground(new Color(Display
					 * .getCurrent(), 255, 255, 255));
					 */
					languagesCombo.setEnabled(false);
				}
			}
		});

		Composite snippetCodeTextComposite = new Composite(this, SWT.None);
		snippetCodeTextComposite.setLayout(new FillLayout(SWT.HORIZONTAL
				| SWT.VERTICAL));
		snippetCodeText = new Text(snippetCodeTextComposite, SWT.MULTI
				| SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		snippetCodeText.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		snippetCodeText.setText("Insert Code Here...");
		GridData snippetCodeTextLayoutGridData = new GridData(SWT.FILL,
				SWT.FILL, true, true, 1, 1);
		snippetCodeTextLayoutGridData.widthHint = 200;
		snippetCodeTextLayoutGridData.heightHint = 565;
		snippetCodeTextComposite.setLayoutData(snippetCodeTextLayoutGridData);

		Composite bottomButtonComposite = new Composite(this, SWT.None);
		GridLayout gridLayoutButton = new GridLayout();
		gridLayoutButton.numColumns = 3;
		bottomButtonComposite.setLayout(gridLayoutButton);
		Button closeButton = new Button(bottomButtonComposite, SWT.NONE);
		closeButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		GridData gd_closeButton = new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 2);
		gd_closeButton.widthHint = 215;
		closeButton.setLayoutData(gd_closeButton);
		closeButton.setText("Discard");
		closeButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					dispose();
					SnippetsView.getInstance().createExplorer();
					System.out.println("Button pressed");
					break;
				}
			}
		});
		Button saveButton = new Button(bottomButtonComposite, SWT.NONE);
		saveButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		GridData gd_saveButton = new GridData(SWT.FILL, SWT.CENTER, true, true,
				1, 2);
		gd_saveButton.widthHint = 215;
		saveButton.setLayoutData(gd_saveButton);
		saveButton.setText("Save");
		Button useSnippetButton = new Button(bottomButtonComposite, SWT.NONE);
		useSnippetButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		GridData gd_useSnippetButton = new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 2);
		gd_useSnippetButton.widthHint = 215;
		useSnippetButton.setLayoutData(gd_useSnippetButton);
		useSnippetButton.setText("Use");
		useSnippetButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					// TEST ARRAY
					ArrayList<Variable> parameters = new ArrayList<Variable>(
							Arrays.asList(new Variable("temp1", "int"),
									new Variable( "temp2", "double"),
									new Variable("temp3", "String")));
					ValueInsertionDialog dialog = new ValueInsertionDialog(
							viewArea.getShell(),
							"Value Insertion",
							"Please fill the boxes with the appropriate values",
							parameters);
					dialog.create();
					if (dialog.open() == Window.OK) {
						// TEST RESULTS
						for (Variable variable : dialog
								.getVariable())
							System.out.println("Variable Name: "
									+ variable.getName() + " Variable Type: " + variable.getType() +
									" Value: " + variable.getValue());
					}
					break;
				}
			}
		});
		bottomButtonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(gridData);
		this.redraw();
	}

	private void setLanguagesBox(final Composite viewArea) {
		File root = SnippetsView.getInstance().getSnippetsRootFolder();
		languages = new ArrayList<String>();
		if (root.isDirectory()) {
			File[] subFolders = root.listFiles();
			for (int i = 0; i < subFolders.length; i++) {
				if (subFolders[i].isDirectory()) {
					languages.add(subFolders[i].getName());
				}
			}
			languagesCombo.setItems(languages.toArray(new String[languages
					.size()]));
			languagesCombo.add("Create New Language...");
		}
		languagesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (languagesCombo.getText().equals("Create New Language...")) {
					NewLanguageDialog dialog = new NewLanguageDialog(viewArea
							.getShell(), "Create New Language",
							"Create A New Language to classify Snippets",
							"Language Name");
					dialog.create();
					if (dialog.open() == Window.OK) {
						String languageName = dialog.getLanguageName();
						languagesCombo.add(languageName,
								languagesCombo.getItemCount() - 1);
						languagesCombo.select(languagesCombo
								.indexOf(languageName));
					}
				}
			}
		});
	}

	private void setSnippetTextAndName() {
		snippetNameTextBox.setText(fileOperations.getFileName());
		String code = "";
		for (String s : fileOperations.getFileCode())
			code += s + "\n";
		snippetCodeText.setText(code);
	}

	private void setSelectedFileLanguage() {
		languagesCombo.select(languages.indexOf(fileOperations.getFileType()));
	}

	private void selectDefaultLanguage() {
		int index = languages.indexOf("Unknown");
		if (index != -1) {
			languagesCombo.select(index);
		}
	}
}