package pa.iscde.formulas.tools;

import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class Calculator implements PidescoTool {

	@Override
	public void run(boolean activate) {
		System.out.println("CALCULATOR MODE");
		FormulasView.setCalculatorMode();
	}

	
}
