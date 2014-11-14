package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;

public class TrigonometricFormula extends Formula{
	
	@Override
	public String name() {
		return "Trignometric";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")&&inputs[1].equals("")){
			return "Inputs not valid";
		}else{
			
		String cosx_str = inputs[0];
		String senx_str = inputs[1];
		if(cosx_str.equals("")){
			double senx = Double.parseDouble(senx_str);
			double aux = 1-(Math.pow(senx, 2));
			return String.valueOf(Math.sqrt(aux));
		}else if(senx_str.equals("")){
			double cosx = Double.parseDouble(cosx_str);
			double aux2 = 1-(Math.pow(cosx, 2));
			return String.valueOf(Math.sqrt(aux2));
		}else
			return "Inputs not valid";
		}
	}
	

	@Override
	public String[] inputs() {
		String[] inputs = {"cos(x)","sin(x)"};
		return inputs;
	}
	
	

}
