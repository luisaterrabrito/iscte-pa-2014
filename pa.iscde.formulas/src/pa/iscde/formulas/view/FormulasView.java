package pa.iscde.formulas.view;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.formulas.Formula;
import pa.iscde.formulas.NewFormula;
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
import pa.iscde.formulas.listeners.CodeEjectorListener;
import pa.iscde.formulas.statistic.Mean;
import pa.iscde.formulas.statistic.Median;
import pa.iscde.formulas.statistic.StandardDeviation;
import pa.iscde.formulas.statistic.Variance;
import pa.iscde.formulas.util.DrawEquationUtil;
import pa.iscde.formulas.util.EquationFinder;
import pa.iscde.formulas.util.FileReaderUtil;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

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
	private static HashMap<Label,Text> formulasBoard;
	
	private static FormulasView formulasView;
	private static JavaEditorServices javaeditor;
	private static File fileTarget;
	
	
	public FormulasView() {
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext context = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaeditor = context.getService(ref);
		
		FormulasView.javaeditor=javaeditor;
		//FormulasView.fileTarget = javaeditor.getOpenedFile();
		loadFormulas();
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
	
	
	private void loadFormulas() {
		String[] aux = FileReaderUtil.readFile().split("END");
		if(!aux.equals("")){
		for (int i = 0; i < aux.length; i++) {
			createFormula(aux[i]);
		}
		}
	}
	
	private void createFormula(String string) {
		String[] lines1 = string.split("222");
		String[] lines2  = lines1[1].split("333");
		String line_1 = lines1[0].split("111")[1];
		String line_2 = lines2[0];
		String line_3  = lines2[1];
		String categoryString = line_1.split(",")[0];
		String formulaName = line_1.split(",")[1];
		int inputsNumber = Integer.parseInt(line_1.split(",")[2]);
		String [] inputs = new String[inputsNumber];
		for (int i = 0; i < inputsNumber; i++) {
			if(i==inputsNumber-1)
				inputs[i] = line_1.split(",")[3].split(";")[i].split("\\s")[0];
			else
				inputs[i] = line_1.split(",")[3].split(";")[i];
		}
		switch (categoryString) {
		case "Basics":
			basic_formulas.add(new NewFormula(formulaName, inputs, line_2.replace("«", System.lineSeparator()), line_3.replace("«", System.lineSeparator())));
			break;
		case "Engineering":
			engineering_formulas.add(new NewFormula(formulaName, inputs, line_2.replace("«", System.lineSeparator()), line_3.replace("«", System.lineSeparator())));
			break;
		case "Statistic":
			statistics_formulas.add(new NewFormula(formulaName, inputs, line_2.replace("«", System.lineSeparator()), line_3.replace("«", System.lineSeparator())));
			break;
		case "Finance":
			finance_formulas.add(new NewFormula(formulaName, inputs, line_2.replace("«", System.lineSeparator()), line_3.replace("«", System.lineSeparator())));
			break;

		}
	}


	public static FormulasView getInstance(){
		return formulasView;
	}
	
	public static void setTarget(JavaEditorServices javaeditor, File file){
		FormulasView.javaeditor = javaeditor;
		FormulasView.fileTarget = file;
	}
	
	
	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {
		FormulasView.formulasView=this;
		FormulasView.viewArea = viewArea;
		viewArea.setLayout(new GridLayout(2,false));
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
	    	CodeEjectorListener codeejector = (CodeEjectorListener) formula.getCodeEjectorListener();
    		codeejector.setTarget(javaeditor);
	    	button.addSelectionListener(codeejector);
	    	buttons.put(button,formula);
	    }
	 
	    return c;
	}


	public static void setFormulaInjector(){
		if(drawFormulas){
			clearFormulasBoard();
			createTabs();
		}
		drawFormulas = false;
		for (Button button : buttons.keySet()) {
			if(buttons.get(button).getCurrentListener()!=null)
				button.removeSelectionListener(buttons.get(button).getCurrentListener());
			
			CodeEjectorListener codeEjectorListener = (CodeEjectorListener) buttons.get(button).getCodeEjectorListener();
			codeEjectorListener.setTarget(javaeditor);
			button.addSelectionListener(codeEjectorListener);
		}
	}


	private static void clearFormulasBoard() {
		if(!formulasBoard.isEmpty()){
			for (Label label : formulasBoard.keySet()) {
				label.dispose();
			}
			for (Text text : formulasBoard.values()) {
				text.dispose();
			}
			formulasBoard.clear();
		}
	}


	public static void setCalculatorMode() {
		if(drawFormulas){
			clearFormulasBoard();
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
		formulasBoard = new HashMap<Label,Text>();
		clearFormulasBoard();
		//viewArea.setBackground(new Color(viewArea.getDisplay(), 255,255,255));
		EquationFinder eq = new EquationFinder(fileTarget);
		for (String equation : eq.getEquations().keySet()) {
			DrawEquationUtil formulaImage = new DrawEquationUtil(viewArea,equation); 
			Text text = new Text(viewArea, SWT.NONE);
			String lines = "";
			int aux = 0;
				for (Integer line : eq.getEquations().get(equation)) {
					aux ++;
					if(eq.getEquations().get(equation).size()>1 && aux!= eq.getEquations().get(equation).size())
						lines +=line+",";
					else
						lines +=line;
				}
			text.setText("Line: "+ lines);
			Label label = new Label(viewArea,SWT.NONE);
			label.setImage(formulaImage.getImage());
			formulasBoard.put(label,text);
		}
		viewArea.pack();
	}
	

}
