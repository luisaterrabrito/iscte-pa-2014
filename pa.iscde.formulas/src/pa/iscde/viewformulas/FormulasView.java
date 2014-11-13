package pa.iscde.viewformulas;

import java.io.IOException;
import java.util.ArrayList;
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

import pa.iscde.formulas.Formula;
import pa.iscde.formulas.basics.PythagoreanTheorem;
import pa.iscde.formulas.basics.QuadraticFormula;
import pa.iscde.formulas.basics.TrigonometricFormula;
import pa.iscde.formulas.engineering.T;
import pt.iscte.pidesco.extensibility.PidescoView;

public class FormulasView implements PidescoView {
	
	
	private HashMap<String,LinkedList<Formula>> allFormulas = new HashMap<String, LinkedList<Formula>>();
	private LinkedList<Formula> basic_formulas = new LinkedList<Formula>();
	private LinkedList<Formula> engineering_formulas = new LinkedList<Formula>();
	private LinkedList<Formula> finance_formulas = new LinkedList<Formula>();
	private LinkedList<Formula> statistics_formulas = new LinkedList<Formula>();
	
	
	private Composite viewArea;
	
	public FormulasView() throws IOException, InstantiationException, IllegalAccessException {
		//list with math basic formulas
		//final List<PojoClass> pojoClasses = PojoClassFactory.getPojoClassesRecursively("pa.iscde.formulas.basics", null);
		//for (PojoClass pojoClass : pojoClasses) {
//			Formula f = (Formula) pojoClass.getClazz().newInstance();
//			System.out.println(f.name());
			//System.out.println(pojoClass);
		//}
		basic_formulas.add(new QuadraticFormula());
		basic_formulas.add(new TrigonometricFormula());
		
		basic_formulas.add(new PythagoreanTheorem());
		engineering_formulas.add(new T());
		//map with all formulas
		allFormulas.put("Basic",basic_formulas);
		allFormulas.put("Engineering",engineering_formulas);
		allFormulas.put("Finance",finance_formulas);
		allFormulas.put("Statistics",statistics_formulas);
	}
	
	
	
	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {
		this.viewArea = viewArea;
		viewArea.setLayout(new GridLayout());
		TabFolder tabFolder = new TabFolder(viewArea, SWT.CLOSE);  
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		tabFolder.setLayoutData(data);
		for (String area : allFormulas.keySet()) {
			TabItem tab = new TabItem(tabFolder,SWT.CLOSE);
			tab.setText(area);
			tab.setControl(createTabContent(tabFolder,allFormulas.get(area)));
		}	
        viewArea.pack();
	}
	
	
	private Composite createTabContent( Composite parent, LinkedList<Formula> formulas ) {
		 
	    Composite c = new Composite( parent, SWT.NONE );
	    c.setLayout( new GridLayout( formulas.size(), false ));
	 
	    for( Formula formula : formulas ) {
	    	Button button = new Button(c, SWT.PUSH);
	    	button.setText(formula.name());
	    	button.addSelectionListener(new Listener(formula));
	    }
	 
	    return c;
	}
	
	
	
	public class Listener implements SelectionListener{
		
		private Formula formula;
		private ArrayList<Text> inputs_text = new ArrayList<Text>();
		
		public Listener(Formula formula) {
			this.formula=formula;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			final Shell dialog = new Shell(viewArea.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			createWindow(dialog);
		}
		
		private void createWindow(final Shell dialog) {
			dialog.setText(formula.name()+" Formula Inputs:");
			dialog.setLayout(new GridLayout(2, true));
			
			
			for (String input : formula.inputs()) {
				Label Input = new Label(dialog, SWT.NONE);
				Input.setText(""+input+":");
				Input.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				Text input_text = new Text(dialog, SWT.BORDER);
				input_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				((GridData) input_text.getLayoutData()).widthHint = 200;
				inputs_text.add(input_text);
			}

			Button ok = new Button(dialog, SWT.PUSH);
			ok.setText("OK");
			ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			ok.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					String[] results = new String[inputs_text.size()];
					for (int i = 0; i < results.length; i++) {
						if(inputs_text.get(i).getText()==null || inputs_text.get(i).getText() == "" || !isNumeric(inputs_text.get(i).getText()))
							results[i] = ""+0;
						else
							results[i] = inputs_text.get(i).getText();
					}
					String solution = formula.result(results);
					System.out.println(solution);
					dialog.close();
				}
			});

			Button cancel = new Button(dialog, SWT.PUSH);
			cancel.setText("Cancel");
			cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			cancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					dialog.close();
				}
			});

			dialog.setDefaultButton(ok);
			dialog.pack();
		    dialog.open();
			
		}
		
		private boolean isNumeric(String str){
		    return str.matches("-?\\d+(.\\d+)?");
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}

}
