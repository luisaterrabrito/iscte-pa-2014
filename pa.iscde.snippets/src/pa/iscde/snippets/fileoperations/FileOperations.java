package pa.iscde.snippets.fileoperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import pa.iscde.snippets.gui.SnippetsView;

public class FileOperations {
	
	private File fileToUse;
	
	public FileOperations(File f) {
		fileToUse = f;
	}

	public String getFileName() {
		return fileToUse.getName().replace(".snp", "");
	}

	public ArrayList<String> getFileCode() {
		ArrayList<String> lines = new ArrayList<String>();
		fileToUse.setReadOnly();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileToUse));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			;
		} catch (IOException e) {
			System.err.println("File Not Found");
		}
		;
		return lines;
	}
	
	public String getFileType(){
		return fileToUse.getParent().replace(SnippetsView.getInstance().getSnippetsRootFolder().getAbsolutePath() + "\\", "");
	}
	
	public void save(String name, String code, String language){
		//TODO: Check if file exists, if not create in the appropriate folder
		//TODO: Save to file used Name and Code from snippetNameTextBox and snippetCodeText
	}
}
