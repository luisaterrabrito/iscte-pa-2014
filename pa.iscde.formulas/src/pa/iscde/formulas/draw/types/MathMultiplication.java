package pa.iscde.formulas.draw.types;

import pa.iscde.formulas.extensibility.DrawEquationsProvider;

public class MathMultiplication implements DrawEquationsProvider{

	@Override
	public String setJavaOperation() {
		return "*";
	}

	@Override
	public String setOperationLatexFormat(String line) {
		if(!line.contains(setJavaOperation()))
			return line;
		return line.replace(setJavaOperation(),"*");
	}

}
