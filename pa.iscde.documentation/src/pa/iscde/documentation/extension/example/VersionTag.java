package pa.iscde.documentation.extension.example;

import pa.iscde.documentation.extension.ITagContentProvider;

public class VersionTag implements ITagContentProvider {

	@Override
	public String getHtmlTitle() {
		return "<br><font size='3' color='red'><b><i>Versão:</i></b></font>";
	}
	
	@Override
	public StringBuilder getHtml(String desc) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br><font size='2' color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;" + desc + "</font>");
		
		return sb;
	}

}
