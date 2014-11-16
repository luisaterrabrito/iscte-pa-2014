package pa.iscde.formulas.engineering;

import pa.iscde.formulas.Formula;

public class DecibelConverter extends Formula{

	@Override
	public String[] inputs() {
		String[] inputs = {"P[W]"};
		return inputs;
	}
	@Override
	public String name() {
		return "Decibel Converter";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double p = Double.parseDouble(inputs[0]);
		double dbw = (Math.log10(p))*10;
		double dbm = dbw+30;
		return dbw+"dBW e " + dbm + "dBm";
		}
	}
	
	

}
