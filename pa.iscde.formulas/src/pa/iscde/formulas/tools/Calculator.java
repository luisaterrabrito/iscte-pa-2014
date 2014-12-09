package pa.iscde.formulas.tools;

import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.extensibility.PidescoTool;

/**
 * Class that activates Calculator tool
 * 
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public class Calculator implements PidescoTool {

	@Override
	public void run(boolean activate) {
		if(activate){
			System.out.println("CALCULATOR MODE");
			FormulasView.getInstance().setCalculatorMode();
		}else{
			System.out.println("FORMULA INJECTOR MODE");
			FormulasView.getInstance().setFormulaInjector();
		}
	}

	
}
