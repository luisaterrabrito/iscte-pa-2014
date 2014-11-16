package pa.iscde.formulas.statistic;

import java.util.Arrays;
import java.util.List;

import pa.iscde.formulas.Formula;

public class Mean extends Formula{

	@Override
	public String[] inputs() {
		String [] input = {"Values[Separate the values with comma]"} ;
		return input;
	}

	@Override
	public String name() {
		return "Mean";
	}

	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double [] values = getValues(inputs[0]);
		double aux=0;
		for (int i = 0; i < values.length; i++) {
			aux+=values[i];
		}
		System.out.println("aux:" + aux + " tamanho: " + values.length);
		double result = aux/values.length;
		return String.valueOf((result));
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
