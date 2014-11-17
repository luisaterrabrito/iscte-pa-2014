package pa.iscde.formulas;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadFormulaFromFile {
	private final static String END_FORMULA ="End";
	private String formulaName;
	private ArrayList<String> textFromFile = new ArrayList<>();
	private ArrayList<NewFormula> allNewFormulas = new ArrayList<NewFormula>();
	private ArrayList<String> algorithm = new ArrayList<String>();
	private ArrayList<String> javaCode = new ArrayList<String>();

	public ReadFormulaFromFile(String formulaName) {
		this.formulaName=formulaName;

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("formulas\\NewFormulas.txt");
		Scanner s = new Scanner(is);
		while(s.hasNext()){
			String string = s.nextLine();
			if(string.contains("1#")){
				textFromFile.add(string);
			}else if(string.contains("2#")){
				algorithm.add(string);
			}else if(string.contains("3#")){
				javaCode.add(string);
			}else if(string.equals(END_FORMULA)){
				textFromFile.add(string);
				addFormula();
				textFromFile.clear();
				javaCode.clear();
				algorithm.clear();
			}
		}
		s.close();
	}

	private void addFormula() {
		List<String> values = Arrays.asList(textFromFile.get(0).split(","));
		List<String> inputs = Arrays.asList(values.get(3).split("@"));
		String [] inputsArray = new String[inputs.size()];
		for (int i = 0; i < inputs.size(); i++) {
			inputsArray[i] = inputs.get(i);
		}
		NewFormula nf = new NewFormula(values.get(0), values.get(1), Integer.parseInt(values.get(2)),inputsArray , algorithm, javaCode);
		allNewFormulas.add(nf);
	}

}

