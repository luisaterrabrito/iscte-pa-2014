package pa.iscde.formulas.tools;

import java.io.IOException;

import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.extensibility.PidescoTool;

/**
 * Class that activates Draw Equations tool
 * 
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */

public class DrawEquations implements PidescoTool {
	
	@Override
	public void run(boolean activate) {
			System.out.println("DRAW EQUATION MODE");
			try {
				FormulasView.getInstance().setDrawEquationMode(null);
			} catch (IOException e) {
			e.printStackTrace();
			}
	}


}

