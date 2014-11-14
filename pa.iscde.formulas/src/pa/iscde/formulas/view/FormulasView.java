package pa.iscde.formulas.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

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
	
	private static HashMap<Button,Formula> buttons = new HashMap<Button,Formula>();
	
	
	
	public FormulasView() {
		
		 
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
		System.out.println("IN");
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
    		button.addSelectionListener(formula.getCodeEjectorListener());
	    	buttons.put(button,formula);
	    }
	 
	    return c;
	}


	public static void setFormulaEjector(){
		
	}


	public static void setCalculatorMode() {
		for (Button button : buttons.keySet()) {
			if(buttons.get(button).getCurrentListener()!=null)
				button.removeSelectionListener(buttons.get(button).getCurrentListener());
			button.addSelectionListener(buttons.get(button).getCalculatorListener());
		}
	}
	
	
	
	

}
