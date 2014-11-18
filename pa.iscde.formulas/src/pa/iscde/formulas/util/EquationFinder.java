package pa.iscde.formulas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class EquationFinder {
	
	char aux1 = '"';
	char aux2 = '+';
	
	private Multimap<String,Integer> equations = ArrayListMultimap.create();
	
	public EquationFinder(File file) throws FileNotFoundException {
		analyseFile(file);
	}

	private void analyseFile(File file) throws FileNotFoundException {
		int lines = 1;
		Scanner s = new Scanner(file);
		while(s.hasNext()){
			String line = s.nextLine();
			if(line.contains("/") || line.contains("Math.sqrt"))
				equations.put(delimitateLine(line),lines);
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

	public Multimap<String,Integer> getEquations(){
		return equations;
	}

}
