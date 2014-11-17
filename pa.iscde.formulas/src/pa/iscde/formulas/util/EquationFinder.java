package pa.iscde.formulas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class EquationFinder {
	
	char aux1 = '"';
	char aux2 = '+';
	
	private HashMap<Integer,String> equations = new HashMap<Integer,String>();
	
	public EquationFinder(File file) throws FileNotFoundException {
		analyseFile(file);
	}

	private void analyseFile(File file) throws FileNotFoundException {
		int lines = 1;
		Scanner s = new Scanner(file);
		while(s.hasNext()){
			String line = s.nextLine();
			if(line.contains("/") || line.contains("Math.sqrt"))
				equations.put(lines,delimitateLine(line));
			lines++;
		}
		s.close();
	}

	private String delimitateLine(String line) {
		String result = line.replace("(", "{").replace(")","}").replace("String", "").replace("int", "").replace(Character.toString(aux1), "").replace(";","").replace("return", "");
		return convertMath(result);
	}
	
	private String convertMath(String line) {
		String result = line;
		if(line.contains("Math.sqrt")){
			result = result.replace("Math.sqrt", "\\sqrt");
		}
		return result;
	}

	public HashMap<Integer,String> getEquations(){
		return equations;
	}

}
