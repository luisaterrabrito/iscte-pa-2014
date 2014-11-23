package pa.iscde.snippets.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	private ArrayList<String> languages;
	private Button editButton;
	
	public SnippetCode(File f, Composite viewArea, int style) {
		super(viewArea, style);
		fileOperations = new FileOperations(f);
		createContents();
		setSnippetTextAndName();
		setSelectedFileLanguage();
		snippetNameTextBox.setEnabled(false);
    	snippetCodeText.setEnabled(false);
    	languagesCombo.setEnabled(false);
	}

	public SnippetCode(Composite viewArea, int style) {
		super(viewArea, style);
		createContents();
		editButton.setSelection(true);
		selectDefaultLanguage();
	}

	public void createContents() {
		this.setLayout(new GridLayout(1, false));
		Composite nameAndEditAndLanguageSelectComposite = new Composite(this,
				SWT.None);
		GridLayout gl_nameAndEditAndLanguageSelectComposite = new GridLayout(6, false);
		gl_nameAndEditAndLanguageSelectComposite.marginWidth = 0;
		nameAndEditAndLanguageSelectComposite.setLayout(gl_nameAndEditAndLanguageSelectComposite);
		GridData gd_nameAndEditAndLanguageSelectComposite = new GridData(
				GridData.FILL_HORIZONTAL);
		gd_nameAndEditAndLanguageSelectComposite.verticalAlignment = SWT.FILL;
		nameAndEditAndLanguageSelectComposite.setLayoutData(gd_nameAndEditAndLanguageSelectComposite);
		
		Composite snippetNameComposite = new Composite(
				nameAndEditAndLanguageSelectComposite, SWT.None);
		snippetNameComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		snippetNameComposite.setLayout(null);
		
		// Snippet Name Label
		Label snippetNameTextLabel = new Label(snippetNameComposite, SWT.None);
		snippetNameTextLabel.setBounds(4, 5, 81, 15);
		snippetNameTextLabel.setText("Snippet Name: ");
		
		// Snippet Name Text Box
		snippetNameTextBox = new Text(snippetNameComposite, SWT.FILL);
		snippetNameTextBox.setText("New Snippet (1)");
		snippetNameTextBox.setBounds(91, 5, 126, 15);
		
		Composite composite = new Composite(nameAndEditAndLanguageSelectComposite, SWT.NONE);
		composite.setLayout(null);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		Label languagesLabel = new Label(composite, SWT.None);
		languagesLabel.setBounds(10, 5, 58, 15);
		languagesLabel.setText("Language: ");
		languagesCombo = new Combo(composite, SWT.FILL | SWT.READ_ONLY);
		languagesCombo.setBounds(74, 1, 183, 23);
		setLanguagesBox();
		new Label(nameAndEditAndLanguageSelectComposite, SWT.NONE);
		new Label(nameAndEditAndLanguageSelectComposite, SWT.NONE);
		new Label(nameAndEditAndLanguageSelectComposite, SWT.NONE);
		editButton = new Button(nameAndEditAndLanguageSelectComposite, SWT.CHECK);
		editButton.setText("Edit");
		editButton.addSelectionListener(new SelectionAdapter()
		{
		    @Override
		    public void widgetSelected(SelectionEvent e)
		    {
		        if (editButton.getSelection()){
		        	snippetNameTextBox.setEnabled(true);
		        	snippetCodeText.setEnabled(true);
		        	languagesCombo.setEnabled(true);
		        }else{
		        	snippetNameTextBox.setEnabled(false);
		        	snippetCodeText.setEnabled(false);
		        	languagesCombo.setEnabled(false);
		        }     
		    }
		});
		
		
		Composite snippetCodeTextComposite = new Composite(this, SWT.None);
		snippetCodeTextComposite.setLayout(new FillLayout(SWT.HORIZONTAL
				| SWT.VERTICAL));
		snippetCodeText = new Text(snippetCodeTextComposite, SWT.MULTI
				| SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		snippetCodeText.setText("Insert Code Here...");
		GridData snippetCodeTextLayoutGridData = new GridData(SWT.FILL,
				SWT.FILL, true, true, 1, 1);
		snippetCodeTextLayoutGridData.widthHint = 200;
		snippetCodeTextLayoutGridData.heightHint = 580;
		snippetCodeTextComposite.setLayoutData(snippetCodeTextLayoutGridData);

		Composite bottomButtonComposite = new Composite(this, SWT.None);
		GridLayout gridLayoutButton = new GridLayout();
		gridLayoutButton.numColumns = 3;
		bottomButtonComposite.setLayout(gridLayoutButton);
		Button closeButton = new Button(bottomButtonComposite, SWT.NONE);
		GridData gd_closeButton = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 2);
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
		GridData gd_saveButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_saveButton.widthHint = 215;
		saveButton.setLayoutData(gd_saveButton);
		saveButton.setText("Save");
		Button useSnippetButton = new Button(bottomButtonComposite, SWT.NONE);
		GridData gd_useSnippetButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_useSnippetButton.widthHint = 215;
		useSnippetButton.setLayoutData(gd_useSnippetButton);
		useSnippetButton.setText("Use");
		bottomButtonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
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