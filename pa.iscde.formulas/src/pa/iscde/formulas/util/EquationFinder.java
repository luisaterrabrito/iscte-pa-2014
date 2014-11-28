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
			if(line.contains("/") || line.contains("Math.sqrt") || line.contains("Math.pow") || line.contains("*"))
				equations.put(delimitateLine(removeA(frac(line))),lines);
			lines++;
		}
		s.close();
	}
	
	public String removeA(String str){
		String aux = str;
		aux = aux.replace(" ", "");

		boolean copy = false;
		String finalStr = "";

		for (int i = 0; i < aux.length()-1; i++) {
			char currentChar = aux.charAt(i);
			char nextChar = aux.charAt(i+1);

			if(currentChar == '+' && nextChar == '"'){
				copy = false;
			} else if(currentChar == '"' && nextChar == '+'){
				copy = false;
			} else {
				if(copy){
					finalStr += currentChar;
				}

				if(!copy){
					copy = true;
				}
			}
		}
		return finalStr;
	}

	private String delimitateLine(String line) {
		//x = (-b+(Math.sqrt(root)))/(2*a); ----> x = \\frac{-b+\\sqrt{root}}{2*a}
		String result = line.replace("(", "{").replace(")","}").replace("String", "").replace("int", "").replace(Character.toString(aux1), "").replace(";","").replace("return", "");
		return convertMath(result);
	}
	
	private String convertMath(String line) {
		String result = line;
		if(line.contains("Math.sqrt")){
			result = result.replace("Math.sqrt", "\\sqrt");
		}else if(line.contains("Math.pow")){
			result = convertPow(line);
		}
		return result;
	}

	private String convertPow(String line) {
		String aux = line.replace("Math.pow(", "");
		String result = aux.split(",")[0];
		System.out.println(aux.split(",")[1]);
		for (int i = 1; i < aux.split(",").length; i++) {
			
		}
		
		
		
		
		return result;
	}

	public Multimap<String,Integer> getEquations(){
		return equations;
	}
	
	public static String frac(String str) {
		
		if(!str.contains("/"))
			return str;
		
		

		int indexOfDiv = str.indexOf("/");
		int counter = 0;
		boolean foundParenteses = false;
		int indexBeginFrac = -1;

		// Encontra inicio
		for (int i = indexOfDiv; i > 0; i--) {
			if(str.charAt(i) == ')'){
				counter++;
				foundParenteses = true;
			}

			if(str.charAt(i) == '(')
				counter--;

			if(foundParenteses && counter == 0){
				indexBeginFrac = i;
				return str.substring(0, indexBeginFrac) + "\\frac" + str.substring(indexBeginFrac, indexOfDiv) + str.substring(indexOfDiv + 1, str.length());
			}
			
			
		}
		return str;
			
	}

}
