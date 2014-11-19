package pa.iscde.formulas.tools;

import pa.iscde.formulas.listeners.AddFormulaListener;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class AddFormula implements PidescoTool{

	@Override
	public void run(boolean activate) {
		new AddFormulaListener();
		System.out.println("ADD NEW FORMULA MODE");

	}

}
