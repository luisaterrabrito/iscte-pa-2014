package pa.iscde.formulas.finance;

import java.util.Arrays;
import java.util.List;

import pa.iscde.formulas.Formula;

public class VALCalculation extends Formula {

	@Override
	public String[] inputs() {
		String[] inputs = {"Number of years", "Cash Flows [Separate the values with comma] ", "Rate[%]"};
		return inputs;
	}

	@Override
	public String name() {
		return "VAL";
	}

	@Override
	public String result(String[] inputs) {
		int n = Integer.parseInt(inputs[0]);
		double[] cashFlows = getCashFlows(inputs[1]);
		double rate = (Double.parseDouble(inputs[2])/100);
		double aux =0;
		for (int i = 0; i < n; i++) {
			aux += auxiliarFunction(i,cashFlows[i], rate); 
		}
		return String.valueOf(aux);
	}

	private double auxiliarFunction(int i, double cfi,double rate) {
		double operation = Math.pow((1+rate),i);
		return (cfi/operation);
	}

	private double[] getCashFlows(String string) {
		List<String> cf = Arrays.asList(string.split(","));
		double [] cashflows = new double[cf.size()];
		for (int i = 0; i < cf.size(); i++) {
			cashflows[i] = Double.parseDouble(cf.get(i));
		}
		return cashflows;
	}

}
