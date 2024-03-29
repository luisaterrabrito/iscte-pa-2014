package pa.iscde.formulas.statistic;

import java.util.Arrays;
import java.util.List;

import pa.iscde.formulas.Formula;

public class Variance extends Formula{
	
	@Override
	public String[] inputs() {
		String [] input = {"Values[Separate the values with comma]"} ;
		return input;
	}

	@Override
	public String name() {
		return "Variance";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double [] values = getValues(inputs[0]);
		double aux=0;
		double mean = getMean(values);
		for (int i = 0; i < values.length; i++) {
			aux+=(mean-values[i])*(mean-values[i]);
		}
		return String.valueOf((aux/values.length));
		}
	}

	private double getMean(double[] values) {
		double mean=0;
		for (int i = 0; i < values.length; i++) {
			mean+=values[i];
		}
		return mean/values.length;
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
