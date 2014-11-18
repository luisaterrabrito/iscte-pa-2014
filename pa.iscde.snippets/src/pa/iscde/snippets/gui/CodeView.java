package pa.iscde.snippets.gui;

import java.io.File;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import pa.iscde.snippets.PidescoView1;
import pt.iscte.pidesco.extensibility.PidescoView;

public class CodeView extends Composite {
//TODO: Change to extend composite
	private File fileToUse;
	
	public CodeView(File f, Composite viewArea, int style) {
		super(viewArea, style);
		fileToUse = f;
		createContents(viewArea);
	}
	
	public CodeView(Composite viewArea, int style){
		super(viewArea, style);
		createContents(viewArea);
	}

	public void createContents(final Composite viewArea) {
		final Composite mainComposite = new Composite(viewArea, SWT.None);
		mainComposite.setLayout(new GridLayout(1, false));

		Composite nameAndEditComposite = new Composite(mainComposite, SWT.None);
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
		Text snippetNameTextBox = new Text(snippetNameComposite, SWT.FILL);
		snippetNameTextBox.setText("Hey");
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

		Composite snippetCodeTextComposite = new Composite(mainComposite,
				SWT.None);
		snippetCodeTextComposite.setLayout(new FillLayout(SWT.HORIZONTAL
				| SWT.VERTICAL));
		Text snippetCodeText = new Text(snippetCodeTextComposite, SWT.MULTI
				| SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		snippetCodeText.setText("Hello");
		GridData snippetCodeTextLayoutGridData = new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1);
		snippetCodeTextLayoutGridData.widthHint = 200;
		snippetCodeTextLayoutGridData.heightHint = 500;
		snippetCodeTextComposite.setLayoutData(snippetCodeTextLayoutGridData);

		Composite bottomButtonComposite = new Composite(mainComposite, SWT.None);
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
					mainComposite.dispose();
					(new PidescoView1()).createContents(viewArea, null);
					viewArea.layout();
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
