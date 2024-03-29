package pa.iscde.formulas;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used to store the formula specifications into a file format.
 * @author Gon�alo Horta & Tiago Saraiva
 *
 */
public class InsertFormulaFormat {

	private String category;
	private String formulaName;
	private int nrInputs;
	private String[] inputs;
	private String algorithm;
	private String codeJava;
	private ArrayList<String> allStringsToTheFile= new ArrayList<String>(); 

	/**
	 * @param category
	 * @param formulaName
	 * @param inputsName
	 * @param inputsNumber
	 * @param algorithm
	 * @param codeJava
	 */
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

	/**
	 * Create the text based on the formula specifications to insert in the text file.
	 * @return bytes
	 */
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
		String secondLine="222"+algorithm.replace("\n", "#"); 
		String thirdLine ="333"+ codeJava.replace("\n", "#");

		allStringsToTheFile.add(firstLine);
		allStringsToTheFile.add(secondLine);
		allStringsToTheFile.add(thirdLine);
		
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
		
		byte[] bytes = baos.toByteArray();
		return bytes;

		}
	
		/**
		 * Returns all the text that will be inserted on the text file.
		 * @return aux
		 */
		public String getFormula(){
			String aux = "";
			for (String string : allStringsToTheFile) {
				aux +=string;
			}
			return aux;
		}

	}
