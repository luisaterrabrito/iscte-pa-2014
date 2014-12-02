package pa.iscde.formulas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

public class FileReaderUtil {

	public static String readFile(){
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath location = root.getLocation();
		IPath append = location.append("formulas");
		
		if(!root.getFolder(append).exists()){
			File directory = append.toFile();
			directory.mkdir();
		}
		
		File dir = append.toFile();
		File[] listFiles = dir.listFiles();
		String allFormulas = "";
		
		if(listFiles.length!=0){
		for (int i = 0; i < listFiles.length; i++) {
			Scanner s;
			try {
				s = new Scanner(listFiles[i]);
				while(s.hasNext()){
					allFormulas+=s.nextLine()+System.lineSeparator();
				}
				allFormulas+="END";
				s.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}else
			return null;
		return allFormulas;
	}
}
