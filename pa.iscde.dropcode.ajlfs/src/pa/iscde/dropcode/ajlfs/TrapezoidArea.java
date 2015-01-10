package pa.iscde.dropcode.ajlfs;

import pa.iscde.formulas.Formula;

public class TrapezoidArea extends Formula {

	@Override
	public String[] inputs() {
		/*
		 * String[0] = b1 (top length); String[1] = b2 (bottom length);
		 * String[2] = h (height);
		 */
		return new String[] { "2", "4", "2" };
	}

	@Override
	public String name() {
		return "Trapezoid Area";
	}

	@Override
	public String result(String[] variables) {

		if (validVariables(variables)) {

			double b1 = Double.parseDouble(variables[0]);
			double b2 = Double.parseDouble(variables[1]);
			double h = Double.parseDouble(variables[2]);

			return String.valueOf((h / 2) * (b1 + b2));
		} else {
			return "The variables of the input are invalid!";
		}
	}

	private boolean validVariables(String[] variables) {
		if (variables != null) {
			for (String v : variables) {
				if (v == null || v.equals("")) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
