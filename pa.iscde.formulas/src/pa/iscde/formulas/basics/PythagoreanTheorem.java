package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;
import pa.iscde.formulas.FormulaClass;

public class PythagoreanTheorem extends FormulaClass implements Formula{

	@Override
	public String name() {
		return "Pythagoreantheorem";
	}

	@Override
	public String result(String[] inputs) {
		String AC_str = inputs[0];
		String OC_str = inputs[1];
		String H_str = inputs[2];
		double ac;
		double oc;
		double h;
		if(AC_str.equals("")){
			oc = Double.parseDouble(OC_str);
			h = Double.parseDouble(H_str);
			return String.valueOf(Math.sqrt((h*h)-(oc*oc)));
		}else if(OC_str.equals("")){
			ac = Double.parseDouble(AC_str);
			h = Double.parseDouble(H_str);
			return String.valueOf(Math.sqrt((h*h)-(ac*ac)));
		}else{
			ac = Double.parseDouble(AC_str);
			oc = Double.parseDouble(OC_str);
			return String.valueOf(Math.sqrt(((ac*ac)+(oc*oc))));
		}
	}
	
	
	@Override
	public String[] inputs() {
		String[] inputs = {"A.C","O.C","H"};
		return inputs;
	}

}
