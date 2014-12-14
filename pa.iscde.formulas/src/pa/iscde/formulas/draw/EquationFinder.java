package pa.iscde.formulas.draw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.formulas.extensibility.DrawEquationsProvider;
import pa.iscde.formulas.util.ConstantsUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


/**
 * Class that analyses each line, and identifies the mathematical equations
 * 
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */

public class EquationFinder {
	
	private Multimap<String,Integer> equations = ArrayListMultimap.create();
	private ArrayList<FormulaAnnotation> annotations = new ArrayList<FormulaAnnotation>();
	private ArrayList<DrawEquationsProvider> providers = new ArrayList<DrawEquationsProvider>();
	
	/**
	 * @param javaeditor 
	 * @param file, represents the open class
	 * @throws FileNotFoundException
	 */
	public EquationFinder(File file) throws FileNotFoundException {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for(IExtension ext : reg.getExtensionPoint("pa.iscde.formulas.newEquationToDraw").getExtensions()) {
			for(IConfigurationElement newEquationToDraw : ext.getConfigurationElements()) {
				try {
					DrawEquationsProvider drawProviders = (DrawEquationsProvider) newEquationToDraw.createExecutableExtension("newOperation");
					providers.add(drawProviders);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		equationAnalyse(file);
	}
	
	
	private void equationAnalyse(File file) throws FileNotFoundException{
		int lines = 1;
		@SuppressWarnings("resource")
		Scanner s = new Scanner(file);
		while(s.hasNext()){
			String line = s.nextLine();
			String line_without_java = removeJavaPrefixes(line);
			String remove_standard_chars = removeStandardChars(line_without_java);
			String latexFormat = null;
			for (DrawEquationsProvider provider : providers) {
				JavaToLatexFormat javaToLatexFormat = new JavaToLatexFormat(provider.setJavaOperation(), provider.setOperationLatexFormat(remove_standard_chars));
				if(remove_standard_chars.contains(javaToLatexFormat.getJavaOperation())){
					System.out.println("JAVA:"+remove_standard_chars);
					latexFormat = javaToLatexFormat.getOperationLatexFormat();
					remove_standard_chars = latexFormat;
					System.out.println("LATEX:"+remove_standard_chars +" | "+javaToLatexFormat.getJavaOperation());
					
				}
			}
			if(latexFormat!=null)
				equations.put(latexFormat,lines); 
			lines++;
		}
//		if(line.contains("/") || line.contains("Math.sqrt") || line.contains("Math.pow") || line.contains("*")){
//		equations.put(delimitateLine(removeA(frac(line))),lines);
//		annotations.add(new FormulaAnnotation("Formula "+i,offset,line.length()));
//	}
	
	}
	
	

	private String removeStandardChars(String line) {
		String aux = removeDoubleChars(line);
		return aux.replace(";", "").replace(Character.toString('"'), "").replace("and", "")
				.replace("(", "{").replace(")","}").replace("+=", "=").replace("-=", "=").replace("Math.PI","\\pi");
	}


	private String removeJavaPrefixes(String line) {
		String aux = line;
		for (String javaprefix : ConstantsUtil.getJavaPrefixs()) {
			if(line.contains(javaprefix))
				aux = line.replace(javaprefix, "");
		}
		return aux;
	}

	private String removeDoubleChars(String str){
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


	
	public ArrayList<FormulaAnnotation> getAnnotations(){
		return annotations;
	}

	public Multimap<String,Integer> getEquations(){
		return equations;
	}
	

}
