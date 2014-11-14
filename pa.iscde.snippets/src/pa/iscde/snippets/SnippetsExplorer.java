package pa.iscde.snippets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.List;

public class SnippetsExplorer extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SnippetsExplorer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		Composite searchComposite = new Composite(this, SWT.NONE);
		GridLayout gl_searchComposite = new GridLayout(2, false);
		gl_searchComposite.marginRight = -5;
		gl_searchComposite.marginLeft = -5;
		searchComposite.setLayout(gl_searchComposite);
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		text = new Text(searchComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnNewButton = new Button(searchComposite, SWT.NONE);
		btnNewButton.setText("New Button");

		Composite chooseComposite = new Composite(this, SWT.NONE);
		chooseComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		chooseComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Combo combo = new Combo(chooseComposite, SWT.NONE);
		combo.setText("wdawdwdw");

		Composite snippetsComposite = new Composite(this, SWT.NONE);
		snippetsComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		snippetsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		List list = new List(snippetsComposite, SWT.BORDER | SWT.V_SCROLL);
		list.setItems(new String[] {"wadaw", "awda", "wda", "wdawd", "ad", "wdawdawdaw", "daw", "dawd", "awd", "awd", "aw", "da", "wda", "wda", "wd", "aw", "da", "wd", "aw", "da", "wd", "awd", "aw", "da", "wd", "awd", "aw", "daw", "d", "wad", "awd", "wdw"});

		Composite addComposite = new Composite(this, SWT.NONE);
		addComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Button addNewButton = new Button(addComposite, SWT.NONE);
		addNewButton.setText("Add New Snippet");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
