package pa.iscde.formulas.tools;

import java.io.IOException;

import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class DrawEquations implements PidescoTool {
	
	@Override
	public void run(boolean activate) {
		System.out.println("DRAW EQUATION MODE");
		try {
			FormulasView.setDrawEquaitonMode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

