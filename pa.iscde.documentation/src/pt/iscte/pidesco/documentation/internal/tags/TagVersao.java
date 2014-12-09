package pt.iscte.pidesco.documentation.internal.tags;

import pt.iscte.pidesco.documentation.service.ITagContentProvider;

public class TagVersao implements ITagContentProvider {

	
	@Override
	public StringBuilder getHtml(String desc) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b>Versão</b>: " + desc);
		
		return sb;
	}

}
