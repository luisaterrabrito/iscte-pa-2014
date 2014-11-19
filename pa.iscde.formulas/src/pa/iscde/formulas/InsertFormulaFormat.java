package pa.iscde.formulas;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InsertFormulaFormat {

	private String category;
	private String formulaName;
	private int nrInputs;
	private String[] inputs;
	private String algorithm;
	private String codeJava;
	private ArrayList<String> allStringsToTheFile= new ArrayList<String>(); 

	public InsertFormulaFormat(String category, String formulaName,
			String[] inputsName, int inputsNumber, String algorithm, String codeJava) {
		super();
		this.category = category;
		this.formulaName = formulaName;
		this.nrInputs = inputsNumber;
		this.inputs = inputsName;
		this.algorithm = algorithm;
		this.codeJava = codeJava;
	}

	public  byte [] createText(){
		String allInputs="";
		for (int i = 0; i < inputs.length; i++) {
			if(i==inputs.length-1){
				allInputs+=inputs[i];
			}else{
				allInputs+=inputs[i]+";";
			}
		}
		String firstLine = "111"+category+","+formulaName+","+String.valueOf(nrInputs)+","+allInputs;
		String secondLine="222"+algorithm.replace("\n", "«"); 
		String thirdLine ="333"+ codeJava.replace("\n", "«");
		allStringsToTheFile.add(firstLine);
		allStringsToTheFile.add(secondLine);
		allStringsToTheFile.add(thirdLine);
		
//		String[] splitArrayAlgorithm = algorithm.split("\n");
//		for (int i = 0; i < splitArrayAlgorithm.length; i++) {
//			splitArrayAlgorithm[i]="222"+splitArrayAlgorithm[i];
//		}
//		String[] splitArrayJavaCode = codeJava.split("\n");
//		for (int i = 0; i < splitArrayJavaCode.length; i++) {
//			splitArrayJavaCode[i]="333"+splitArrayJavaCode[i];
//		}
//		allStringsToTheFile.add(firstLine);
//		for (int i = 0; i < splitArrayAlgorithm.length; i++) {
//			allStringsToTheFile.add(splitArrayAlgorithm[i]);
//		}
//		for (int i = 0; i < splitArrayJavaCode.length; i++) {
//			allStringsToTheFile.add(splitArrayJavaCode[i]);
//		}
//		
//		// write to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : allStringsToTheFile) {
		try {
			out.writeUTF(element);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		for (int i = 0; i < allStringsToTheFile.size(); i++) {
			System.out.println(allStringsToTheFile.get(i));
		}
		
		byte[] bytes = baos.toByteArray();
		return bytes;

		}

	}
