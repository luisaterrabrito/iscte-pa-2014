package pa.iscde.formulas.util;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class DrawEquation  {
	
	private String equation;
	
	public DrawEquation(String equation){
		this.equation=equation;
	}
	
	public void render() {
			TeXFormula formula = new TeXFormula(equation);
			TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
	}

}