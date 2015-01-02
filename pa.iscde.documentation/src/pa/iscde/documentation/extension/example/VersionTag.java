package pa.iscde.documentation.extension.example;

import pa.iscde.documentation.extension.ITagContentProvider;

/**
 * This is a example of how to add documentation tags for the Documentation View.
 * 
 * @author David Franco
 * @version 01.00
 */
public class VersionTag implements ITagContentProvider {

	@Override
	public String getHtmlTitle() {
		return "<br><font size='3' color='red'><b><i>Versão</i></b></font>";
	}
	
	@Override
	public StringBuilder getHtml(String description) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br><font size='2' color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;" + description + "</font>");
		
		return sb;
	}

}
