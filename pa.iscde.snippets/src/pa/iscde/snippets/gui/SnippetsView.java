package pa.iscde.snippets.gui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;

import pt.iscte.pidesco.extensibility.PidescoView;

public class SnippetsView implements PidescoView {
	private Composite viewArea;
	private static SnippetsView instance;
	private File snippetsRootFolder;

	public static SnippetsView getInstance() {
		return instance;
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;

		this.viewArea = viewArea;
		
		URL fileURL;
		try {
			fileURL = new URL("platform:/plugin/pa.iscde.snippets/Snippets");
			System.out.println(FileLocator.resolve(fileURL).toString());
			snippetsRootFolder = new File(FileLocator.resolve(fileURL).getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		createExplorer();
	}

	public void createExplorer() {
		new SnippetsExplorer(viewArea, SWT.NONE);
		viewArea.layout();
	}

	// With file
	public void createSnippetCode(File snp) {
		new SnippetCode(snp, viewArea, SWT.NONE);
		viewArea.layout();
	}

	// Without file
	public void createSnippetCode() {
		new SnippetCode(viewArea, SWT.NONE);
		viewArea.layout();
	}
}
