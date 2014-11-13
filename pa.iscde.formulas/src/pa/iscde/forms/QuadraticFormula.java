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
		return null;
	}
	

}
