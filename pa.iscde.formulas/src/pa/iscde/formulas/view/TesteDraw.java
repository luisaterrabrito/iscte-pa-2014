package pa.iscde.formulas.view;

import pa.iscde.formulas.extensibility.DrawEquationsProvider;

public class TesteDraw implements DrawEquationsProvider{

	@Override
	public String setJavaOperation() {
		return "Math.abs";
	}

	@Override
	public String setOperationLatexFormat() {
		return "\\int";
	}

}
