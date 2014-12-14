package pa.iscde.formulas.extensibility;

/**
 * Interface used by the extension point.
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public interface DrawEquationsProvider {
	
	/**
	 * Insert the string to be replaced in your java code
	 * Example : "Math.sqrt"
	 * @return javaType
	 */
	public String setJavaOperation();
	
	/**
	 * Replaces the java formath for the latext format in the line
	 * Example : \\sqrt
	 * @return latexType
	 */
	public String setOperationLatexFormat(String line);

}
