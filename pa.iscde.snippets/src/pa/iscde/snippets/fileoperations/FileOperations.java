package pa.iscde.snippets.fileoperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;

import pa.iscde.snippets.gui.SnippetsView;

public class FileOperations {

	private File fileToUse = null;
	private File snippetsRootFolder;

	public FileOperations(File f) {
		fileToUse = f;
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
		Path languagePath = Paths.get(snippetsRootFolder.getPath() + "/"
				+ language);
		if (!Files.exists(languagePath)) {
			try {
				Path temp = Files.createDirectory(languagePath);
				temp.toFile().setWritable(true, false);
				
			} catch (IOException e) {
				System.err.println("Failed to Create New Directory");
				e.printStackTrace();
			}
		}
		File fileToSave = new File(languagePath + "/" + name + ".snp");
		if (!fileToSave.isFile()) {
			try {
				fileToSave.createNewFile();					
			} catch (IOException e) {
				System.err.println("Failed to Create New File");
				e.printStackTrace();
			}
		}
		try {
			fileToSave.setWritable(true, false);
			BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileToSave.getPath()));
			bw.write(code);
			bw.close();
		} catch (IOException e) {
			System.err.println("Failed to Write To New File");			
			e.printStackTrace();
		}
	}
}
