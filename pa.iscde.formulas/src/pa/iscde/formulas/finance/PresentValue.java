package pa.iscde.formulas.finance;

import pa.iscde.formulas.Formula;

public class PresentValue extends Formula{

	@Override
	public String[] inputs() {
		String[] inputs = {"Rate[%]","FV[Future Value]","n"};
		return inputs;
	}

	@Override
	public String name() {
		return "Present Value";
	}

	@Override
	public String result(String[] inputs) {
		double rate = (Double.parseDouble(inputs[0])/100);
		double futureValue = Double.parseDouble(inputs[1]);
		int n = Integer.parseInt(inputs[2]);
		double aux = Math.pow((1+rate), n);
		
		return String.valueOf((futureValue/aux));
	}

	
}
