package pa.iscde.snippets.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
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

import pa.iscde.snippets.fileoperations.FileOperations;

public class SnippetCode extends Composite {
	private Text snippetNameTextBox;
	private Text snippetCodeText;
	private FileOperations fileOperations;
	private Combo languagesCombo;
	ArrayList<String> languages;

	public SnippetCode(File f, Composite viewArea, int style) {
		super(viewArea, style);
		fileOperations = new FileOperations(f);
		createContents();
		setSnippetTextAndName();
		setSelectedFileLanguage();
	}

	public SnippetCode(Composite viewArea, int style) {
		super(viewArea, style);
		createContents();
		selectDefaultLanguage();
	}

	public void createContents() {
		this.setLayout(new GridLayout(1, false));

		Composite nameAndEditAndLanguageSelectComposite = new Composite(this,
				SWT.None);
		GridLayout gridLayoutNameAndEdit = new GridLayout();
		gridLayoutNameAndEdit.numColumns = 3;
		nameAndEditAndLanguageSelectComposite.setLayout(gridLayoutNameAndEdit);
		nameAndEditAndLanguageSelectComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		Composite snippetNameComposite = new Composite(
				nameAndEditAndLanguageSelectComposite, SWT.None);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		snippetNameComposite.setLayout(fillLayout);
		// Snippet Name Label
		Label snippetNameTextLabel = new Label(snippetNameComposite, SWT.None);
		snippetNameTextLabel.setText("Snippet Name: ");
		// Snippet Name Text Box
		snippetNameTextBox = new Text(snippetNameComposite, SWT.FILL);
		snippetNameTextBox.setEditable(true);
		snippetNameTextBox.setSize(50, 50);
		// Snippet Type ComboBox
		Composite chooseComposite = new Composite(
				nameAndEditAndLanguageSelectComposite, SWT.NONE);
		chooseComposite.setLayout(fillLayout);
		languagesCombo = new Combo(chooseComposite, SWT.NONE | SWT.READ_ONLY);
		setLanguagesBox();

		Composite editComposite = new Composite(
				nameAndEditAndLanguageSelectComposite, SWT.None);
		GridLayout gridLayoutEdit = new GridLayout();
		gridLayoutEdit.numColumns = 2;
		editComposite.setLayout(gridLayoutEdit);
		gridLayoutEdit.marginRight = -5;
		gridLayoutEdit.marginLeft = -5;
		editComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Button editButton = new Button(editComposite, SWT.CHECK);
		editButton.setText("Edit");
		editComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Composite snippetCodeTextComposite = new Composite(this, SWT.None);
		snippetCodeTextComposite.setLayout(new FillLayout(SWT.HORIZONTAL
				| SWT.VERTICAL));
		snippetCodeText = new Text(snippetCodeTextComposite, SWT.MULTI
				| SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		snippetCodeText.setText("Insert Code Here...");
		GridData snippetCodeTextLayoutGridData = new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1);
		snippetCodeTextLayoutGridData.widthHint = 200;
		snippetCodeTextLayoutGridData.heightHint = 500;
		snippetCodeTextComposite.setLayoutData(snippetCodeTextLayoutGridData);

		Composite bottomButtonComposite = new Composite(this, SWT.None);
		GridLayout gridLayoutButton = new GridLayout();
		gridLayoutButton.numColumns = 3;
		bottomButtonComposite.setLayout(gridLayoutButton);
		Button closeButton = new Button(bottomButtonComposite, SWT.PUSH);
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
		Button saveButton = new Button(bottomButtonComposite, SWT.PUSH);
		saveButton.setText("Save");
		Button useSnippetButton = new Button(bottomButtonComposite, SWT.PUSH);
		useSnippetButton.setText("Use");
		bottomButtonComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
	}

	private void setLanguagesBox() {
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