package pa.iscde.documentation.extension;

import pa.iscde.documentation.structure.ObjectDoc;

/**
 * This is the interface to be implemented for adding documentation exports for the
 * Documentation View.
 * 
 * @author João Gonçalves
 * @version 01.00
 * 
 * @see https://code.google.com/p/iscte-pa-2014/wiki/DocumentationExports
 */
public interface IDocumentationExportProvider {

	/**
	 * In this method, you'll need to implement the filter name, for example, HTML Files.
	 * 
	 * @return the filter name
	 */
	public String getFilterName();
	
	/**
	 * In this method, you'll need to implement the filter extension, for example, html.
	 * 
	 * @return the filter extension
	 */
	public String getFilterExtension();
	
	/**
	 * In this method, you'll need to implement the save file.
	 * 
	 * @param fullFileName
	 *            - fullname file (with full path)
	 * @param documentation
	 *            - structure with all the information to produce the exportation
	 * @throws Exception
	 */
	public void saveToFile(String fullFileName, ObjectDoc documentation) throws Exception;
	
}
