package pa.iscde.formulas.draw.types;

import pa.iscde.formulas.extensibility.DrawEquationsProvider;

public class MathPow implements DrawEquationsProvider{

	@Override
	public String setJavaOperation() {
		return "Math.pow";
	}

	@Override
	public String setOperationLatexFormat(String line) {
		if(!line.contains(setJavaOperation()))
			return line;
		String aux = line.replace(setJavaOperation(), "");
		String result = aux.split(",")[0]+"^"+aux.split(",")[1];
		return result;
	}

}
