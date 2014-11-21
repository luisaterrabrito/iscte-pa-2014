package pa.iscde.snippets.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SnippetCode extends Composite {
	private File fileToUse;
	private Text snippetNameTextBox;
	private Text snippetCodeText;

	public SnippetCode(File f, Composite viewArea, int style) {
		super(viewArea, style);
		fileToUse = f;
		createContents();
		setSnippetTextAndName();
	}

	public SnippetCode(Composite viewArea, int style) {
		super(viewArea, style);
		createContents();
	}

	private void setSnippetTextAndName() {
		snippetNameTextBox.setText(getFileName(fileToUse));
		String code = "";
		snippetNameTextBox.setText(getFileName(fileToUse));
		for (String s : getFileCode(fileToUse))
			code += s + "\n";
		snippetCodeText.setText(code);
	}

	private String getFileName(File f) {
		return f.getName().replace(".snp", "");
	}

	private ArrayList<String> getFileCode(File f) {
		ArrayList<String> lines = new ArrayList<String>();
		f.setReadOnly();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(f));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			;
		} catch (IOException e) {
			System.err.println("File Not Found");
		}
		;
		return lines;
	}

	public void createContents() {
		this.setLayout(new GridLayout(1, false));

		Composite nameAndEditComposite = new Composite(this, SWT.None);
		GridLayout gridLayoutNameAndEdit = new GridLayout();
		gridLayoutNameAndEdit.numColumns = 2;
		nameAndEditComposite.setLayout(gridLayoutNameAndEdit);
		nameAndEditComposite.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		Composite snippetNameComposite = new Composite(nameAndEditComposite,
				SWT.None);
		FillLayout fillLayoutName = new FillLayout(SWT.HORIZONTAL);
		snippetNameComposite.setLayout(fillLayoutName);
		// Snippet Name Label
		Label snippetNameTextLabel = new Label(snippetNameComposite, SWT.None);
		snippetNameTextLabel.setText("Snippet Name: ");
		// Snippet Name Text Box
		snippetNameTextBox = new Text(snippetNameComposite, SWT.FILL);
		snippetNameTextBox.setEditable(true);
		snippetNameTextBox.setSize(50, 50);

		Composite editComposite = new Composite(nameAndEditComposite, SWT.None);
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
		snippetCodeText.setText("Hello");
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
}
