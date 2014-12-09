package pa.iscde.formulas.view;



import pa.iscde.formulas.Formula;

public class Teste extends Formula{
	@Override
	public String name() {
		return "PresentValue";
	}
	
	@Override
	public String methodCode(String file) {
		return "private double PresentValue(double ratePercentage, double futureValue, int n ) {\n"
				+ "\tdouble rate = (ratePercentage/100);\n"
				+ "\tdouble aux = Math.pow((1+rate), n);\n"
				+ "return (futureValue/aux);\n}";
	}
	
	@Override
	public String result(String[] inputs) {
		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
		double rate = (Double.parseDouble(inputs[0])/100);
		double futureValue = Double.parseDouble(inputs[1]);
		int n = Integer.parseInt(inputs[2]);
		double aux = Math.pow((1+rate), n);
		
		return String.valueOf((futureValue/aux));
		}
	}
	

	@Override
	public String[] inputs() {
		String[] inputs = {"Rate[%]","FV[Future Value]","n"};
		return inputs;
	}

}
