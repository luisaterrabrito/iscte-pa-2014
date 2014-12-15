package pa.iscde.indent.extensibility;



import pa.iscde.indent.model.CodeIndentOptions;

/**
 * This class represent the code indent extensibility protocol.
 * @author t-saribe
 *
 */
public interface ICodeIndenter {
	
	/**
	 * Get the indented string correspondent to file that is opened (on top) in the editor.
	 * @param file (non-null), the current opened file.
	 * @param Object with indent options [indent size, max repeated break Lines, wrap line size, braces position]
	 * @return Code indented as a string.
	 */
	public String indent(String file, CodeIndentOptions options);
	
	/**
	 * Check if the file extension string provided is supported.
	 * @param fileExtension as a string
	 * @return this should return a boolean indicating if this current file extension is supported.
	 */
	public boolean suportFormat(String fileExtension);
}
