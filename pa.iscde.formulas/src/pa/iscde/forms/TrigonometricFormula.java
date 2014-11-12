package pa.iscde.forms;

public class TrigonometricFormula implements Formula{
	
	@Override
	public String setName() {
		return "trigonometric";
	}
	
	@Override
	public String setCode(String[] inputs) {
		String aux = "teste";
		for (int i = 0; i < inputs.length; i++) {
			aux+=inputs[i];
		}
		return aux;
	}

}
