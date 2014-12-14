package pa.iscde.formulas.draw.types;

import pa.iscde.formulas.extensibility.DrawEquationsProvider;

public class MathLog implements DrawEquationsProvider{

	@Override
	public String setJavaOperation() {
		return "Math.log10";
	}

	@Override
	public String setOperationLatexFormat(String line) {
		return line.replace(setJavaOperation(), "\\log_{10}");
	}

}
