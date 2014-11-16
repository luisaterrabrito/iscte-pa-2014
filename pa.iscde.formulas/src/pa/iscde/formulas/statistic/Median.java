package pa.iscde.formulas.statistic;

import java.util.Arrays;
import java.util.List;

import pa.iscde.formulas.Formula;

public class Median extends Formula{

	@Override
	public String[] inputs() {
		String [] input = {"Values[Separate the values with comma]"} ;
		return input;
	}

	@Override
	public String name() {
		return "Median";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double [] values = getValues(inputs[0]);
		double [] b = new double[values.length];
		System.arraycopy(values, 0, b, 0, b.length);
		Arrays.sort(values);
		
		if(values.length%2==0){
			double aux = ((b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0);
			return String.valueOf(aux);
		}else{
			double aux = b[b.length / 2];
			return String.valueOf(aux);
		}
		}
	}

	private double[] getValues(String string) {
		List<String> cf = Arrays.asList(string.split(","));
		double [] values = new double[cf.size()];
		for (int i = 0; i < cf.size(); i++) {
			values[i] = Double.parseDouble(cf.get(i));
		}
		return values;
	}

	
}
