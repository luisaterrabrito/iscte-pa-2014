package pa.iscde.formulas.engineering;

import pa.iscde.formulas.Formula;

public class FriisFormula extends Formula{
	
	@Override
	public String name() {
		return "FriisFormula";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double pt = Double.valueOf(inputs[0]);
		double gt = Double.valueOf(inputs[1]);
		double gr = Double.valueOf(inputs[2]);
		double wavelength = Double.valueOf(inputs[3]);
		double distance = Double.valueOf(inputs[4]);
		
		double topCalculation = ((Math.pow(wavelength, 2))*gt*gr);
		double aux = 4*Math.PI*distance;
		double downCalculation = Math.pow(aux, 2);
		double result = ((topCalculation/downCalculation)*pt);
		return String.valueOf(result);
		}
	}	
	

	@Override
	public String[] inputs() {
		String[] inputs = {"Pt (Transmited Power)","Gt (Antenna gain)","Gr (Antenna gain)", "Wavelength" , "Distance"};
		return inputs;
	}
	

}
