package pa.iscde.formulas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ReadUtil {
	
	public static String read(String plugin, String name){
		Bundle bundle = Platform.getBundle(plugin);
		URL fileURL = bundle.getEntry(name);
		File file = null;
		try {
		    file = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		
		String result = "";
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			while(s.hasNext()){
				result+=s.nextLine()+"\n";
			}
		s.close();
		return result;
	}
	
}
