package pa.iscde.formulas.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import pa.iscde.formulas.Formula;
import pa.iscde.formulas.basics.Areas;
import pa.iscde.formulas.basics.PythagoreanTheorem;
import pa.iscde.formulas.basics.QuadraticFormula;
import pa.iscde.formulas.basics.TrigonometricFormula;
import pa.iscde.formulas.basics.Volumes;
import pa.iscde.formulas.engineering.DecibelConverter;
import pa.iscde.formulas.engineering.ElectronicsFormulas;
import pa.iscde.formulas.engineering.FriisFormula;
import pa.iscde.formulas.engineering.MovementEquations;
import pa.iscde.formulas.finance.NumberOfPayments;
import pa.iscde.formulas.finance.PresentValue;
import pa.iscde.formulas.finance.VALCalculation;
import pa.iscde.formulas.statistic.Mean;
import pa.iscde.formulas.statistic.Median;
import pa.iscde.formulas.statistic.StandardDeviation;
import pa.iscde.formulas.statistic.Variance;
import pa.iscde.formulas.util.DrawEquationUtil;
import pt.iscte.pidesco.extensibility.PidescoView;

public class FormulasView implements PidescoView {
	
	private static HashMap<String,LinkedList<Formula>> allFormulas = new HashMap<String, LinkedList<Formula>>();
	private static HashMap<Button,Formula> buttons = new HashMap<Button,Formula>();
	
	private LinkedList<Formula> basic_formulas = new LinkedList<Formula>();
	private LinkedList<Formula> engineering_formulas = new LinkedList<Formula>();
	private LinkedList<Formula> finance_formulas = new LinkedList<Formula>();
	private LinkedList<Formula> statistics_formulas = new LinkedList<Formula>();
	
	private static Composite viewArea;
	private static TabFolder tabFolder;
	private static boolean drawFormulas = false;
	private static Label formulasBoard;
	
	
	
	
	
	public FormulasView() {
		
		 
		basic_formulas.add(new QuadraticFormula());
		basic_formulas.add(new TrigonometricFormula());		
		basic_formulas.add(new PythagoreanTheorem());
		basic_formulas.add(new Areas());
		basic_formulas.add(new Volumes());
		
		engineering_formulas.add(new FriisFormula());
		engineering_formulas.add(new DecibelConverter());
		engineering_formulas.add(new MovementEquations());
		engineering_formulas.add(new ElectronicsFormulas());
		
		finance_formulas.add(new VALCalculation());
		finance_formulas.add(new NumberOfPayments());
		finance_formulas.add(new PresentValue());
		
		statistics_formulas.add(new Mean());
		statistics_formulas.add(new Median());
		statistics_formulas.add(new StandardDeviation());
		statistics_formulas.add(new Variance());
		
		//map with all formulas
		allFormulas.put("Basic",basic_formulas);
		allFormulas.put("Engineering",engineering_formulas);
		allFormulas.put("Finance",finance_formulas);
		allFormulas.put("Statistics",statistics_formulas);
		
		
	}
	
	
	
	
	
	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {
		FormulasView.viewArea = viewArea;
		viewArea.setLayout(new GridLayout());
		createTabs();
	}
	
	
	private static void createTabs() {
		tabFolder = new TabFolder(viewArea, SWT.FILL);  
		for (String area : allFormulas.keySet()) {
			TabItem tab = new TabItem(tabFolder,SWT.CLOSE);
			tab.setText(area);
			tab.setControl(createTabContent(tabFolder,allFormulas.get(area)));
		}	
        viewArea.pack();
	}

	private static Composite createTabContent(Composite parent, LinkedList<Formula> formulas ) {
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


	public static void setFormulaInjector(){
		if(drawFormulas){
			formulasBoard.dispose();
			createTabs();
		}
		drawFormulas = false;
		for (Button button : buttons.keySet()) {
			if(buttons.get(button).getCurrentListener()!=null)
				button.removeSelectionListener(buttons.get(button).getCurrentListener());
			button.addSelectionListener(buttons.get(button).getCodeEjectorListener());
		}
	}


	public static void setCalculatorMode() {
		if(drawFormulas){
			formulasBoard.dispose();
			createTabs();
		}
		drawFormulas = false;
		for (Button button : buttons.keySet()) {
			if(buttons.get(button).getCurrentListener()!=null)
				button.removeSelectionListener(buttons.get(button).getCurrentListener());
			button.addSelectionListener(buttons.get(button).getCalculatorListener());
		}
	}



	public static void setDrawEquaitonMode() throws IOException {
		drawFormulas = true;
		buttons.clear();
		tabFolder.dispose();
		DrawEquationUtil formulaImage = new DrawEquationUtil(viewArea,"\\int\\frac {V_m} {K_M+S}"); 
		formulasBoard = new Label(viewArea,SWT.NONE);
		formulasBoard.setImage(formulaImage.getImage());
		viewArea.pack();
	}
	
	
	
	

}
