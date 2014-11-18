package pa.iscde.snippets;

import java.io.File;

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

import pa.iscde.snippets.gui.CodeView;

public class SnippetsExplorer extends Composite {
	private Text text;
	final private Composite PARENT;
	final private File SNIPPETS_FOLDER = null; 

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SnippetsExplorer(final Composite parent, int style) {
		super(parent, style);
		this.PARENT = parent;
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
		//My listener - Diogo
		Listener listener =  new Listener() {
		      public void handleEvent(Event e) {
			        switch (e.type) {
			        case SWT.Selection:
			        	new CodeView();
			        	parent.layout();
			        	System.out.println("Button pressed");
			        	break;
			        }
			      }
			    };
		addNewButton.setText("Add New Snippet");
		addNewButton.addListener(SWT.Selection, listener);
	}

	private void loadSnippets(){
		
	}
}
