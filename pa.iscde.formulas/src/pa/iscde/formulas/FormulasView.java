package pa.iscde.formulas;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import pa.iscde.forms.Formula;
import pa.iscde.forms.QuadraticFormula;
import pa.iscde.forms.TrigonometricFormula;
import pt.iscte.pidesco.extensibility.PidescoView;

public class FormulasView implements PidescoView {
	
	
	private HashMap<String,LinkedList<Formula>> allFormulas = new HashMap<String, LinkedList<Formula>>();
	private LinkedList<Formula> basic_formulas = new LinkedList<Formula>();
	private Composite viewArea;
	
	public FormulasView() {
		basic_formulas.add(new QuadraticFormula());
		basic_formulas.add(new TrigonometricFormula());
		allFormulas.put("Basic",basic_formulas);
		allFormulas.put("Engineering",basic_formulas);
//		allFormulas.put("Finance",basic_formulas);
//		allFormulas.put("Statistics",basic_formulas);
	}
	
	
	
	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {
		this.viewArea = viewArea;
		viewArea.setLayout(new GridLayout());
		TabFolder tabFolder = new TabFolder(viewArea, SWT.CLOSE);  
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		tabFolder.setLayoutData(data);
		SashForm sashForm1 = new SashForm(tabFolder, SWT.VERTICAL);
		
		for (String area : allFormulas.keySet()) {
			TabItem tab = new TabItem(tabFolder,SWT.CLOSE);
			tab.setText(area);
			System.out.println(area);
			for (Formula formula : allFormulas.get(area)) {
				System.out.println(formula.setName());
				Button button = new Button(sashForm1, SWT.NONE);
				button.setImage(imageMap.get(formula.setName()+".gif"));
				button.addSelectionListener(new Listener());
			}
			System.out.println("--------------");
			tab.setControl(sashForm1);	 
		}	
		
        viewArea.pack();
	}
	
	public class Listener implements SelectionListener{

		@Override
		public void widgetSelected(SelectionEvent e) {
			final Shell dialog = new Shell(viewArea.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			createWindow(dialog);
		}
		
		private void createWindow(Shell dialog) {
			dialog.setText("Quadratic Formula Inputs:");
			dialog.setLayout(new GridLayout(2, true));

			Label Input1 = new Label(dialog, SWT.NONE);
			Input1.setText("Input a");
			Input1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Text input1_text = new Text(dialog, SWT.BORDER);
			input1_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			((GridData) input1_text.getLayoutData()).widthHint = 200;

			
			Label input2 = new Label(dialog, SWT.NONE);
			input2.setText("Input b");
			input2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Text input2_text = new Text(dialog, SWT.BORDER);
			input2_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			
			Label input3 = new Label(dialog, SWT.NONE);
			input3.setText("Input c");
			input3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			Text input3_text = new Text(dialog, SWT.BORDER);
			input3_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			
			
			
			Button ok = new Button(dialog, SWT.PUSH);
			ok.setText("OK");
			ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			ok.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					
				}
			});

			Button cancel = new Button(dialog, SWT.PUSH);
			cancel.setText("Cancel");
			cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			cancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					
				}
			});

			dialog.setDefaultButton(ok);
			
			dialog.pack();
		    dialog.open();
			
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}

}
