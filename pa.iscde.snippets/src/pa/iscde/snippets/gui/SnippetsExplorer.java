package pa.iscde.snippets.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SnippetsExplorer extends Composite {
	private Text text;
	private HashMap<File, ArrayList<File>> loadedSnippets;
	private HashMap<String, File> filteredSnippets;
	private List snippetsList;
	private Combo languagesCombo;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SnippetsExplorer(final Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		Composite searchComposite = new Composite(this, SWT.NONE);
		GridLayout gl_searchComposite = new GridLayout(2, false);
		gl_searchComposite.marginRight = -5;
		gl_searchComposite.marginLeft = -5;
		searchComposite.setLayout(gl_searchComposite);
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		text = new Text(searchComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnNewButton = new Button(searchComposite, SWT.NONE);
		btnNewButton.setText("New Button");

		Composite chooseComposite = new Composite(this, SWT.NONE);
		chooseComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		chooseComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		languagesCombo = new Combo(chooseComposite, SWT.NONE);

		Composite snippetsComposite = new Composite(this, SWT.NONE);
		snippetsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		snippetsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, true, 1, 1));

		snippetsList = new List(snippetsComposite, SWT.BORDER | SWT.V_SCROLL);

		Composite addComposite = new Composite(this, SWT.NONE);
		addComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		addComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Button addNewButton = new Button(addComposite, SWT.NONE);

		// My listener - Diogo
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					dispose();
					SnippetsView.getInstance().createSnippetCode();
					parent.layout();
					System.out.println("Button pressed");
					break;
				}
			}
		};
		addNewButton.setText("Add New Snippet");
		addNewButton.addListener(SWT.Selection, listener);

		loadSnippets();
	}

	private void loadSnippets() {
		loadedSnippets = new HashMap<>();
		filteredSnippets = new HashMap<>();

		languagesCombo.removeAll();
		languagesCombo.add("All");

		File root = SnippetsView.getSnippetsRootFolder();
		if (root.isDirectory()) {
			File[] subFolders = root.listFiles();
			for (int i = 0; i < subFolders.length; i++) {
				if (subFolders[i].isDirectory()) {
					loadedSnippets.put(subFolders[i], new ArrayList<File>());
					languagesCombo.add(subFolders[i].getName());
					File[] snippets = subFolders[i].listFiles();
					for (int j = 0; j < snippets.length; j++) {
						if (!snippets[j].isDirectory())
							loadedSnippets.get(subFolders[i]).add(snippets[j]);
					}
				}
			}
			for (File snippetsOfLanguage : loadedSnippets.keySet()) {
				ArrayList<File> snippets = loadedSnippets
						.get(snippetsOfLanguage);
				for (File file : snippets) {
					filteredSnippets.put(file.getName(), file);
				}
			}
		}

		refreshSnippetsList();
	}

	private void refreshSnippetsList() {
		snippetsList.removeAll();
		snippetsList.setItems(filteredSnippets.keySet().toArray(
				new String[filteredSnippets.keySet().size()]));
	}
}
