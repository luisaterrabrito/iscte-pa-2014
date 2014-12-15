package pa.iscde.formulas.util;

import java.util.ArrayList;

public class ConstantsUtil {
	
	public static final String PLUGIN_ID = "pa.iscde.formulas";
	
	public static ArrayList<String> getJavaPrefixs(){
		ArrayList<String> javaprefixs = new ArrayList<String>();
		javaprefixs.add("return");
		javaprefixs.add("int");
		javaprefixs.add("double");
		javaprefixs.add("int[]");
		javaprefixs.add("String");
		javaprefixs.add("public");
		javaprefixs.add("static");
		javaprefixs.add("private");
		return javaprefixs;
	}

}
