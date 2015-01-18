package pa.iscde.filtersearch.debas;

import pa.iscde.documentation.extension.ITagContentProvider;

public class AuthorTag implements ITagContentProvider {

	@Override
	public String getHtmlTitle() {
		return "<br><font size='20' color='blue'><b>Autores</b></font>";
	}
	
	@Override
	public StringBuilder getHtml(String description) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br><font size='2' color='black'>&nbsp;&nbsp;&nbsp;&nbsp;" + description + "</font></br>");
		
		return sb;
	}
}
