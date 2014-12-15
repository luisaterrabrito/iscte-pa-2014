package pa.iscde.snippets.fileoperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;

/*
 * Class that contains various functions to work with files
 */
public class FileOperations {

	private File fileToUse = null;
	private File snippetsRootFolder;

	public FileOperations(File f) {
		fileToUse = f;
		initialize();
	}

	public FileOperations() {
		initialize();
	}

	private void initialize() {
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
		if(fileToUse == null)
			return null;
		return fileToUse.getName().replace(".snp", "");
	}

	public ArrayList<String> getFileCode() {
		if(fileToUse == null)
			return null;
		ArrayList<String> lines = new ArrayList<String>();
		fileToUse.setReadOnly();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileToUse));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("File Not Found");
		}
		return lines;
	}

	public String getFileType() {
		if(fileToUse == null)
			return null;
		return fileToUse.getParent().replace(
				snippetsRootFolder.getAbsolutePath() + "\\", "");
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
			BufferedWriter bw = Files.newBufferedWriter(Paths.get(fileToSave
					.getPath()));
			bw.write(code);
			bw.close();
		} catch (IOException e) {
			System.err.println("Failed to Write To New File");
			e.printStackTrace();
		}
		fileToUse = fileToSave;
	}

	// Function that checks if filename Already exists starting from root
	// directory
	// Calls "checkIfNameAlreadyExistsDirectory" if file in root is a directory
	// and will throw true if it finds a file with the same name in directory or
	// subdirectories
	// Calls "checkIfNameAlreadyExistsFile" if file in root is a directory and
	// will throw true if file has the same name
	// Returns false if it doesn't find anything in root or sub-directories
	public boolean checkIfNameAlreadyExists(String text) {
		for (File childDirectory : snippetsRootFolder.listFiles()) {
			if (childDirectory.isDirectory()) {
				if (checkIfNameAlreadyExistsDirectory(text, childDirectory))
					return true;
			} else if (childDirectory.isFile()) {
				if (checkIfNameAlreadyExistsFile(text, childDirectory)) {
					return true;
				}
			}
		}
		return false;
	}

	// Recursive function to check folders, if it finds a file it will call
	// "checkIfNameAlreadyExistsDirectory" to check if Name exists and will
	// return true if it does
	// If it doesn't find a file or a file with the same name it will return
	// false
	private boolean checkIfNameAlreadyExistsDirectory(String text,
			File childDirectories) {
		for (File childFile : childDirectories.listFiles()) {
			if (childFile.isDirectory()) {
				checkIfNameAlreadyExistsDirectory(text, childFile);
			} else if (childFile.isFile()) {
				if (checkIfNameAlreadyExistsFile(text, childFile))
					return true;
			}
		}
		return false;
	}

	// Function to check if File has the same name has the text + .snp
	// returns false if it is the same file
	// returns true if it does
	// false if not
	private boolean checkIfNameAlreadyExistsFile(String text, File f) {
		if (fileToUse != null && f.getName().equals(fileToUse.getName()))
			return false;
		else if (f.getName().equals(text + ".snp"))
			return true;
		return false;
	}

	// Returns the number of the Snippet that should be assigned to a new
	// snippet
	// Checks the lowest snippet number available
	public int numberOfNewSnippet() {
		int aux = 1;
		while (checkIfNameAlreadyExists("New Snippet(" + aux + ")")) {
			aux += 1;
		}
		System.out.println(aux);
		return aux;
	}
}
