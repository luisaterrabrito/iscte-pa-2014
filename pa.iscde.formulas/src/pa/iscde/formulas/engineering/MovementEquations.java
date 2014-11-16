package pa.iscde.formulas.engineering;

import pa.iscde.formulas.Formula;

public class MovementEquations extends Formula{
	
	private final double GRAVITY = 9.8 ;

	@Override
	public String[] inputs() {
		String[] inputs = {"x0","v0x","y0","v0y","t"};
		return inputs;
	}

	@Override
	public String name() {
		return "Movement Equations";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double xo = Double.parseDouble(inputs[0]);
		double vox = Double.parseDouble(inputs[1]);
		double yo = Double.parseDouble(inputs[2]);
		double voy = Double.parseDouble(inputs[3]);
		double t = Double.parseDouble(inputs[4]);
		
		double x = xo + (vox*t);
		double aux= (GRAVITY/2)*Math.pow(t, 2);
		double y = yo +(voy*t) -aux;
		return "x = " + x +"e y = " + y;
		}
	}

}
