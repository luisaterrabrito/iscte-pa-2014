package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;
import pa.iscde.formulas.FormulaClass;

public class TrigonometricFormula extends FormulaClass implements Formula{
	
	@Override
	public String name() {
		return "Trignometric";
	}

	@Override
	public String result(String[] inputs) {
		return "";
	}

	@Override
	public String[] inputs() {
		String[] inputs = {"cos(x)","sin(x)","tan(x)","sad","asdda","sda"};
		return inputs;
	}
	
	

}
