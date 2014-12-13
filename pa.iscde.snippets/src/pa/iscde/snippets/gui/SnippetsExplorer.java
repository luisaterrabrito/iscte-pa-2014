package pa.iscde.snippets.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class SnippetsExplorer extends Composite {
	private static SnippetsExplorer instance = null;
	private GridData gridData;
	private Text searchText;
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
	public static SnippetsExplorer getInstance() {
		return instance;
	}

	public SnippetsExplorer(final Composite parent, int style) {
		super(parent, style);
		instance = this;

		setLayout(new GridLayout(1, false));
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(gridData);

		Composite searchComposite = new Composite(this, SWT.NONE);
		GridLayout gl_searchComposite = new GridLayout(1, false);
		gl_searchComposite.marginRight = -5;
		gl_searchComposite.marginLeft = -5;
		searchComposite.setLayout(gl_searchComposite);
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		searchText = new Text(searchComposite, SWT.BORDER);
		searchText.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.ITALIC));
		searchText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		searchText.setText("Search Snippets...");
		searchText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (searchText.getText().equals("")) {
					filterByLanguage();
				} else {
					if (!searchText.getText().equals("Search Snippets...")) {
						filterByLanguage();
						search();
					}
				}
			}
		});
		searchText.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (searchText.getText().equals("")) {
					searchText.setFont(SWTResourceManager.getFont("Segoe UI",
							11, SWT.ITALIC));
					searchText.setText("Search Snippets...");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				searchText.setFont(SWTResourceManager.getFont("Segoe UI", 11,
						SWT.NORMAL));
				searchText.setText("");
			}
		});

		Composite chooseComposite = new Composite(this, SWT.NONE);
		chooseComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		chooseComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		languagesCombo = new Combo(chooseComposite, SWT.NONE | SWT.READ_ONLY);
		languagesCombo.setFont(SWTResourceManager.getFont("Segoe UI", 11,
				SWT.NORMAL));

		languagesCombo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				filterByLanguage();
			}
		});
		;

		Composite snippetsComposite = new Composite(this, SWT.NONE);
		snippetsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		snippetsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		snippetsList = new List(snippetsComposite, SWT.BORDER | SWT.V_SCROLL
				| SWT.READ_ONLY);
		snippetsList.setFont(SWTResourceManager.getFont("Segoe UI", 12,
				SWT.NORMAL));
		snippetsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				hideUnhide();
				SnippetsView.getInstance().createSnippetCode(
						filteredSnippets.get(snippetsList.getItem(snippetsList
								.getSelectionIndex())));
			}
		});

		Composite addComposite = new Composite(this, SWT.NONE);
		addComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		addComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Button addNewButton = new Button(addComposite, SWT.NONE);
		addNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				hideUnhide();
				SnippetsView.getInstance().createSnippetCode();
			}
		});
		addNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 10,
				SWT.NORMAL));

		addNewButton.setText("Add New Snippet");

		addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				instance = null;
			}
		});

		loadSnippets();
	}

	private void loadSnippets() {
		loadedSnippets = new HashMap<>();
		filteredSnippets = new HashMap<>();

		languagesCombo.removeAll();
		languagesCombo.add("All");
		languagesCombo.select(0);

		File root = SnippetsView.getInstance().getSnippetsRootFolder();
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
			loadAllToFiltered();
		}
		refreshSnippetsList();
	}

	private void loadAllToFiltered() {
		for (File snippetsOfLanguage : loadedSnippets.keySet()) {
			ArrayList<File> snippets = loadedSnippets.get(snippetsOfLanguage);
			for (File file : snippets) {
				filteredSnippets.put(file.getName().replace(".snp", ""), file);
			}
		}
	}

	private void refreshSnippetsList() {
		snippetsList.removeAll();
		snippetsList.setItems(filteredSnippets.keySet().toArray(
				new String[filteredSnippets.keySet().size()]));
	}

	private void filterByLanguage() {
		int selected = languagesCombo.getSelectionIndex();
		if (selected == 0) {
			loadAllToFiltered();
			refreshSnippetsList();
		} else {
			String name = languagesCombo.getItem(selected);
			ArrayList<File> files = null;
			for (File snippetsOfLanguage : loadedSnippets.keySet()) {
				if (snippetsOfLanguage.getName().equals(name)) {
					files = loadedSnippets.get(snippetsOfLanguage);
					break;
				}
			}
			filteredSnippets.clear();
			for (File file : files) {
				filteredSnippets.put(file.getName().replace(".snp", ""), file);
			}
			refreshSnippetsList();
		}
	}

	private void search() {
		if (!searchText.getText().equals("")) {
			HashMap<String, File> found = new HashMap<>();
			for (String name : filteredSnippets.keySet()) {
				if (name.toLowerCase().contains(
						searchText.getText().toLowerCase()))
					found.put(name, filteredSnippets.get(name));
			}
			filteredSnippets = found;
			refreshSnippetsList();
		}
	}

	protected void hideUnhide() {
		gridData.exclude = !gridData.exclude;
		setVisible(!gridData.exclude);
	}
	
	public void setFilterToJava(){
		languagesCombo.select(languagesCombo.indexOf("Java"));
		filterByLanguage();
		search();
	}
}
