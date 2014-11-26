package pa.iscde.snippets.fileoperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;

import pa.iscde.snippets.gui.SnippetsView;

public class FileOperations {

	private File fileToUse;
	private File snippetsRootFolder;

	public FileOperations(File f) {
		fileToUse = f;
		snippetsRootFolder = f.getAbsoluteFile();
	}

	public FileOperations() {
		URL fileURL;
		try {
			fileURL = new URL("platform:/plugin/pa.iscde.snippets/Snippets");
			try {
				snippetsRootFolder = new File(FileLocator.resolve(fileURL)
						.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

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

	public String getFileType() {
		return fileToUse.getParent().replace(
				SnippetsView.getInstance().getSnippetsRootFolder()
						.getAbsolutePath()
						+ "\\", "");
	}

	public void save(String name, String code, String language) {
		// TODO: Check if file exists, if not create in the appropriate folder
		// TODO: Save to file used Name and Code from snippetNameTextBox and
		// snippetCodeText
	}
}
