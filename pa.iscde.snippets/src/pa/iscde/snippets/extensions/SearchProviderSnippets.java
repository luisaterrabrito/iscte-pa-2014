package pa.iscde.snippets.extensions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import pa.iscde.filtersearch.providers.SearchProvider;

public class SearchProviderSnippets implements SearchProvider {
	private static File snippetsRootFolder;
	private HashMap<String, File> snippets;

	public SearchProviderSnippets() {
		loadSnippets();
		URL fileURL;
		try {
			fileURL = new URL("platform:/plugin/pa.iscde.snippets/Snippets");
			snippetsRootFolder = new File(FileLocator.resolve(fileURL)
					.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Object> getResults(String text) {
		ArrayList<Object> result = new ArrayList<Object>();
		if (!text.equals("")) {
			for (String name : snippets.keySet()) {
				if (name.toLowerCase().contains(text.toLowerCase()))
					result.add(name);
			}
		}
		return result;
		// TODO procurar se os snippets contem algo do texto ou se o texto
		// TODO for o nome completo sem case de uma linguagem devolver os
		// snippets da linguagem
	}

	private void loadSnippets() {
		if (snippetsRootFolder.isDirectory()) {
			snippets = new HashMap<String, File>();
			File[] subFolders = snippetsRootFolder.listFiles();
			for (int i = 0; i < subFolders.length; i++) {
				if (subFolders[i].isDirectory()) {
					File[] snippets = subFolders[i].listFiles();
					for (int j = 0; j < snippets.length; j++) {
						if (!snippets[j].isDirectory()) {
							this.snippets.put(
									snippets[j].getName().replace(".snp", ""),
									snippets[j]);
						}
					}
				}
			}
		}
	}

	@Override
	public Image setImage(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		// TODO Auto-generated method stub

	}

}
