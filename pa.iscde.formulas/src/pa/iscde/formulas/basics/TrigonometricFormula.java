package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;

public class TrigonometricFormula implements Formula{
	
	@Override
	public String name() {
		return "trigonometric";
	}

	@Override
	public String methodCode() {
		return null;
	}

	@Override
	public String result(String[] inputs) {
		return null;
	}

	@Override
	public String[] inputs() {
		String[] inputs = {"cos(x)","sin(x)","tan(x)","sad","asdda","sda"};
		return inputs;
	}
	
	

}
