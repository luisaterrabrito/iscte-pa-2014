package pa.iscde.indent.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pa.iscde.indent.extensibility.ICodeIndenter;
import pa.iscde.indent.model.CodeIndentOptions;
import pa.iscde.indent.model.Tuple;

public class CodeIndentJava implements ICodeIndenter {

	List<String> supportedFiles = Arrays.asList("java", "js", "json");

	private List<Tuple> replaceRules = new ArrayList<Tuple>();

	public CodeIndentJava() {
		// Removes whitespace between a word character and . or ,
		replaceRules.add(new Tuple(
				"(if|for|foreach|while)( ?\\(.+\\))(\\s+)(\\w)", "$1$2 $3"));
		// Removes whitespace between a word character and . or ,
		replaceRules.add(new Tuple("(try|finally|catch)(\\s+)(\\w)", "$1 $3"));
		// Removes whitespace between a word character and . or ,
		replaceRules.add(new Tuple("(\\w;?)(\\s+)([\\,;\r?\n{}])", "$1$3"));
		// Removes whitespace between a word character and . or ,
		replaceRules.add(new Tuple("([;])(?!\r?\n)", "$1\r\n"));
		// Removes whitespace between a word character and . or ,
		replaceRules.add(new Tuple("(?<!\r?\n)([{}])", "\r\n$1"));
		// Removes whitespace between a word character and . or ,
		replaceRules.add(new Tuple("([{}])(?!\r?\n)", "$1\r\n"));
	}

	public String applyRules(String jsonString) {

		String fileCopy = jsonString;

		for (Tuple tuple : replaceRules) {
			fileCopy = fileCopy.replaceAll(tuple.regex, tuple.replacement);
		}

		String lines[] = fileCopy.split("\\r?\\n");

		String index = "";
		String output = "";
		for (String line : lines) {

			if (index.length() > 0 && line.equals("}"))
				index = index.substring(1);

			output += index + line.trim() + "\r\n";

			if (line.equals("{"))
				index += "\t";

		}

		return output;
	}

	@Override
	public boolean suportFormat(String fileExtension) {
		// TODO Auto-generated method stub
		
		return supportedFiles.contains(fileExtension);
	}

	@Override
	public String indent(String file, CodeIndentOptions options) {
		// TODO Auto-generated method stub
		
		String ret = applyRules(file);

		return ret;
	}
}