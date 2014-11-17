package pa.iscde.formulas;

import java.util.ArrayList;

public class NewFormula {
	
	private String category;
	private String formulaName;
	private int inputNumbers;
	private String [] inputs;
	private ArrayList<String> algorithm;
	private ArrayList<String> javaCode;
	
	
	public NewFormula(String category, String formulaName, int inputNumbers,
			String[] inputs, ArrayList<String> algorithm,
			ArrayList<String> javaCode) {
		this.category=category;
		this.formulaName=formulaName;
		this.inputNumbers=inputNumbers;
		this.inputs=inputs;
		this.algorithm=algorithm;
		this.javaCode=javaCode;
		
		imprimir();
	}
	private void imprimir() {
		System.out.println("Formula adicionada: " + getCategory() + " Nome: " + getFormulaName()+ " inputNumber: " + getInputNumbers() + 
				" inputs: ");
		for (int i = 0; i < inputs.length; i++) {
			System.out.println(inputs[i]);
		}
		System.out.println("algoritmo: ");
		for (int i = 0; i < algorithm.size(); i++) {
			System.out.println(algorithm.get(i));
		}
		System.out.println("java code: ");
		for (int i = 0; i < javaCode.size(); i++) {
			System.out.println(javaCode.get(i));
		}
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFormulaName() {
		return formulaName;
	}
	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}
	public int getInputNumbers() {
		return inputNumbers;
	}
	public void setInputNumbers(int inputNumbers) {
		this.inputNumbers = inputNumbers;
	}
	public String[] getInputs() {
		return inputs;
	}
	public void setInputs(String[] inputs) {
		this.inputs = inputs;
	}
	public ArrayList<String> getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(ArrayList<String> algorithm) {
		this.algorithm = algorithm;
	}
	public ArrayList<String> getJavaCode() {
		return javaCode;
	}
	public void setJavaCode(ArrayList<String> javaCode) {
		this.javaCode = javaCode;
	}
	

}
