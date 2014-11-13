package pa.iscde.forms;

public class QuadraticFormula implements Formula{

	@Override
	public String setName() {
		return "quadratic";
	}

	@Override
	public String methodCode() {
		String methodCode = "private int quadraticFormula(int a, int b, int c){\n";
		return methodCode;
	}

	@Override
	public String result(String[] inputs) {
		return calculateQuadraticFormula(inputs);
	}

	private String calculateQuadraticFormula(String[] inputs) {
		double a =Double.parseDouble(inputs[0]);
		double b =Double.parseDouble(inputs[1]);
		double c =Double.parseDouble(inputs[2]);

		double x;
		double y;
		double d;

		if((d = b*b - 4*a*c) < 0){
			return "Não há soluções";
		}
		else {
			x= (-b+Math.sqrt(d))/(2*a);
			y= (-b-Math.sqrt(d))/(2*a);
			return "A solução é x= " + x + " e x=" + y;   
		}
	}


}
