package pa.iscde.formulas.util;

import java.io.InputStream;
import java.util.Scanner;

public class ReadUtil {
	
	public static String read(String file){
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();//"formulas\\"+name()+".txt"
		InputStream is = classloader.getResourceAsStream(file);
		String result = "";
		Scanner s = new Scanner(is);
			while(s.hasNext()){
				result+=s.nextLine()+"\n";
			}
		s.close();
		return result;
	}

}
