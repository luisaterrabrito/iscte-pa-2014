package pa.iscde.formulas.finance;

import pa.iscde.formulas.Formula;

public class NumberOfPayments extends Formula{

	@Override
	public String[] inputs() {
		String[] inputs = {"Rate[%]","FV [Future Value]","Periodic Payment"};
		return inputs;
	}

	@Override
	public String name() {
		return "Number of Payments";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double rate = (Double.parseDouble(inputs[0])/100);
		double futureValue = Double.parseDouble(inputs[1]);
		double periodicPayment = Double.parseDouble(inputs[2]);
		double aux = ((rate*futureValue)/periodicPayment);
		double upperOperation = -Math.log10(1-aux); 
		double downOperation = Math.log10(1+rate);
			
		return String.valueOf((upperOperation/downOperation));
		}
	}
	
	

}
