package pa.iscde.documentation.extension;

/**
 * This is the interface to be implemented for adding documentation tags for the
 * Documentation View.
 * 
 * @author David Franco
 * @version 01.00
 * 
 * @see https://code.google.com/p/iscte-pa-2014/wiki/DocumentationTags
 */
public interface ITagContentProvider {

	/**
	 * In this method you will implement the title name that will appear in the
	 * Documentation View.
	 * 
	 * @return the tag title in html
	 */
	public String getHtmlTitle();
	
	/**
	 * In this method you will implement how you want the description appear in
	 * the Documentation View.
	 * 
	 * @param description
	 *            - the description associate to the tag
	 * @return the description in html
	 */
	public StringBuilder getHtml(String description);

}
