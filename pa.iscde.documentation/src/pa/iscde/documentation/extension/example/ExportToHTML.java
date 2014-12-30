package pa.iscde.documentation.extension.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pa.iscde.documentation.extension.IDocumentationExportProvider;
import pa.iscde.documentation.struture.ConstrutorDoc;
import pa.iscde.documentation.struture.MethodDoc;
import pa.iscde.documentation.struture.ObjectDoc;

public class ExportToHTML implements IDocumentationExportProvider {

	@Override
	public String getFilterName() {
		return "HTML Files";
	}

	@Override
	public String getFilterExtension() {
		return "html";
	}

	@Override
	public void saveToFile(String fullfileName, ObjectDoc doc) throws Exception {
		File file = new File(fullfileName);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<br><font size='6'><b><i>Classe: </i></b>" + doc.getFullName() + "</font>");
		if ( doc.getComment().length() != 0 )
			sb.append("<br><font size='2'>" + doc.getComment() + "</font>");
		sb.append("<br>");

		if ( !doc.getTags().isEmpty() ) {
			writeTagsToHtml(sb, doc.getTags().entrySet().iterator());
		}
		sb.append("<br>------------------------------------------------------------------------------------------");

		if ( !doc.getConstrutors().isEmpty() ) {
			for (ConstrutorDoc construtor : doc.getConstrutors()) {
				sb.append("<br><font size='4'><b><i>Construtor: </i></b>" + construtor.getName() + "</font>");
				if ( construtor.getComment() != null && construtor.getComment().length() > 0 )
					sb.append("<br><font size='2'>" + construtor.getComment() + "</font>");
				sb.append("<br>");
	
				writeTagsToHtml(sb, construtor.getTags().entrySet().iterator());
			}
			sb.append("<br>------------------------------------------------------------------------------------------");
		}

		if ( !doc.getMethods().isEmpty() ) {
			for (MethodDoc method : doc.getMethods()) {
				sb.append("<br><font size='4'><b><i>Método: </i></b>" + method.getName() + "</font>");
				if ( method.getComment() != null && method.getComment().length() > 0 )
					sb.append("<br><font size='2'>" + method.getComment() + "</font>");
				sb.append("<br>");
	
				writeTagsToHtml(sb, method.getTags().entrySet().iterator());
			}
			sb.append("<br>------------------------------------------------------------------------------------------");
		}

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(sb.toString());
		    writer.close();
		} catch (IOException e1) {
			throw new IOException();
		}
	}

	private void writeTagsToHtml(StringBuilder sb, Iterator<Entry<String, List<String>>> itTags) {
		if ( itTags.hasNext() ) {
			while (itTags.hasNext()) {
				Map.Entry<String, List<String>> pairs = (Map.Entry<String, List<String>>) itTags.next();
				
				sb.append("<br><font size='3'><b>" + pairs.getKey() + ":</font></b>");
				for (String str : pairs.getValue()) {
					sb.append("<br><font size='2'>&nbsp;&nbsp;&nbsp;&nbsp;" + str + "</font>");
				}
			}
			
			sb.append("<br>");
		}
		sb.append("<br>");
	}

}
