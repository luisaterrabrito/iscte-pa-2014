package pa.iscde.snippets.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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

import activator.SnippetsActivator;
import pa.iscde.snippets.data.Variable;
import pa.iscde.snippets.extensionhandlers.SnippetContextDefinitionManager;
import pa.iscde.snippets.fileoperations.FileOperations;
import pa.iscde.snippets.gui.dialogboxes.NewLanguageDialog;
import pa.iscde.snippets.gui.dialogboxes.ValueInsertionDialog;
import pa.iscde.snippets.interfaces.ContextDefinitionInterface.ValidateMessage;

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
		fileOperations = new FileOperations();
		createContents();
		editButton.setSelection(true);
		selectDefaultLanguage();

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
		snippetNameTextBox.setText("New Snippet("
				+ fileOperations.numberOfNewSnippet() + ")");
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
		editButton.addSelectionListener(editButtonListenerCreator());

		Composite snippetCodeTextComposite = new Composite(this, SWT.None);
		snippetCodeTextComposite.setLayout(new FillLayout(SWT.HORIZONTAL
				| SWT.VERTICAL));
		snippetCodeText = new Text(snippetCodeTextComposite, SWT.MULTI
				| SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		snippetCodeText.setText("Insert Code Here...");
		snippetCodeText.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.ITALIC));
		if (SnippetsActivator.getInstance().getSelectedText() != null
				&& !SnippetsActivator.getInstance().getSelectedText()
						.equals("")) {
			snippetCodeText.setText(SnippetsActivator.getInstance()
					.getSelectedText());
			snippetCodeText.setFont(SWTResourceManager.getFont("Segoe UI", 11,
					SWT.NORMAL));
		}
		snippetCodeText.addFocusListener(focusListenerCreator());
		// TODO: Add Listener To Check If Text Changed, use changed flag
		GridData snippetCodeTextLayoutGridData = new GridData(SWT.FILL,
				SWT.FILL, true, true, 1, 1);
		snippetCodeTextLayoutGridData.widthHint = 200;
		snippetCodeTextLayoutGridData.heightHint = 565;
		snippetCodeTextComposite.setLayoutData(snippetCodeTextLayoutGridData);

		Composite bottomButtonComposite = new Composite(this, SWT.None);
		GridLayout gridLayoutButton = new GridLayout();
		gridLayoutButton.numColumns = 3;
		bottomButtonComposite.setLayout(gridLayoutButton);
		Button discardButton = new Button(bottomButtonComposite, SWT.NONE);
		discardButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		GridData gd_closeButton = new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 2);
		gd_closeButton.widthHint = 215;
		discardButton.setLayoutData(gd_closeButton);
		discardButton.setText("Discard");
		discardButton.addListener(SWT.Selection, addButtonListenerCreator());
		Button saveButton = new Button(bottomButtonComposite, SWT.NONE);
		saveButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		GridData gd_saveButton = new GridData(SWT.FILL, SWT.CENTER, true, true,
				1, 2);
		gd_saveButton.widthHint = 215;
		saveButton.setLayoutData(gd_saveButton);
		saveButton.setText("Save");
		saveButton.addListener(SWT.Selection, saveButtonListenerCreator());
		Button useSnippetButton = new Button(bottomButtonComposite, SWT.NONE);
		useSnippetButton.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
		GridData gd_useSnippetButton = new GridData(SWT.FILL, SWT.CENTER, true,
				true, 1, 2);
		gd_useSnippetButton.widthHint = 215;
		useSnippetButton.setLayoutData(gd_useSnippetButton);
		useSnippetButton.setText("Use");
		useSnippetButton.addListener(SWT.Selection,
				useSnippetButtonListenerCreator());
		bottomButtonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(gridData);
		this.redraw();
	}

	/* Listener Section */
	private SelectionAdapter languagesComboBoxSelectionListenerCreator(
			final Composite viewArea) {
		return new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (languagesCombo.getText().equals("Create New Language...")) {
					NewLanguageDialog dialog = new NewLanguageDialog(
							viewArea.getShell(), "Create New Language",
							"Create A New Language to classify Snippets",
							"Language Name");
					dialog.create();
					if (dialog.open() == Window.OK) {
						boolean aux = false;
						String languageFromUser = dialog.getLanguageName();
						for (String language : languagesCombo.getItems()) {
							if (language.toLowerCase().equals(
									languageFromUser.toLowerCase())) {
								aux = true;
								languageFromUser = language;
								break;
							}
						}
						if (!aux)
							languagesCombo.add(languageFromUser,
									languagesCombo.getItemCount() - 1);
						languagesCombo.select(languagesCombo
								.indexOf(languageFromUser));
					}
				}
			}
		};
	}

	private Listener useSnippetButtonListenerCreator() {
		return new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					HashMap<String, Variable> variables = getVariables();
					if (!variables.isEmpty()) {
						ValueInsertionDialog dialog = new ValueInsertionDialog(
								viewArea.getShell(),
								"Value Insertion",
								"Please fill the boxes with the appropriate values",
								variables);
						dialog.create();
						if (dialog.open() == Window.OK) {
							insertSnippet(variables);
						}
					} else {
						insertSnippet();
					}
					break;
				}
			}
		};
	}

	private Listener saveButtonListenerCreator() {
		return new Listener() {

			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					if (snippetNameTextBox.getText().replaceAll("\\s", "")
							.length() == 0) {
						MessageDialog
								.openWarning(viewArea.getShell(), "Warning",
										"Name field must not be empty. Please change it.");
						snippetNameTextBox.setText("");
					} else if (fileOperations
							.checkIfNameAlreadyExists(snippetNameTextBox
									.getText())) {
						MessageDialog
								.openWarning(
										viewArea.getShell(),
										"Warning",
										"The name you choose is already attributed to another snippet. Please change it.");
					} else {
						fileOperations.save(snippetNameTextBox.getText(),
								snippetCodeText.getText(),
								languagesCombo.getText());
						MessageDialog.openInformation(viewArea.getShell(),
								"Info", "File succesfully saved.");
					}
					break;
				}
			}
		};
	}

	private SelectionAdapter editButtonListenerCreator() {
		return new SelectionAdapter() {
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
		};
	}

	private Listener addButtonListenerCreator() {
		return new Listener() {

			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Selection:
					boolean aux = MessageDialog
							.openConfirm(viewArea.getShell(), "Discard",
									"Changes you may have made will be discarded. Do you wish to exit?");
					if (aux) {
						dispose();
						SnippetsView.getInstance().createExplorer();
						System.out.println("Button pressed");
					}
					break;
				}
			}
		};
	}

	private FocusListener focusListenerCreator() {
		return new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (snippetCodeText.getText().equals("")) {
					snippetCodeText.setFont(SWTResourceManager.getFont(
							"Segoe UI", 11, SWT.ITALIC));
					snippetCodeText.setText("Insert Code Here...");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (snippetCodeText.getText().equals("Insert Code Here...")) {
					snippetCodeText.setFont(SWTResourceManager.getFont(
							"Segoe UI", 11, SWT.NORMAL));
					snippetCodeText.setText("");
				}
			}
		};
	}

	/* Listener Section */

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
		languagesCombo
				.addSelectionListener(languagesComboBoxSelectionListenerCreator(viewArea));
	}

	private void setSnippetTextAndName() {
		snippetNameTextBox.setText(fileOperations.getFileName());
		String code = "";
		for (String s : fileOperations.getFileCode())
			code += s + "\n";
		snippetCodeText.setText(code);
		snippetCodeText.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));
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

	private HashMap<String, Variable> getVariables() {
		HashMap<String, Variable> variables = new HashMap<String, Variable>();
		Scanner scanner = new Scanner(snippetCodeText.getText());
		String token = "";
		while (scanner.hasNextLine()) {
			token = "";
			while (token != null) {
				token = scanner.findInLine("(\\$[\\w]+:[\\w]*)|(\\$[\\w]+)");
				if (token != null) {
					if (token.contains(":")) {
						String[] split = token.split(":");
						variables.put(split[0].replace("$", ""), new Variable(
								token, split[0].replace("$", ""), split[1]));
					} else {
						variables.put(token.replace("$", ""), new Variable(
								token, token.replace("$", "")));
					}
				}
			}
			scanner.nextLine();
		}
		scanner.close();
		return variables;
	}

	private void insertSnippet(HashMap<String, Variable> variables) {
		String code = snippetCodeText.getText();
		for (Variable variable : variables.values()) {
			code = code.replace(variable.getSubstituteToken(),
					variable.getValue());
		}
		SnippetsActivator.getInstance().insertTextAt(code);
	}

	private void insertSnippet() {
		ValidateMessage message = SnippetContextDefinitionManager.getInstance()
				.validateSnippet(fileOperations);
		if (message.isValid())
			SnippetsActivator.getInstance().insertTextAt(
					snippetCodeText.getText());
		else
			MessageDialog.openError(viewArea.getShell(),
					"Invalid Snippet Context", message.getMessage());
	}
}