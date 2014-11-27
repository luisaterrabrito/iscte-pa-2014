package pa.iscde.snippets.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;

public class SnippetsView implements PidescoView {
	private Composite viewArea;
	private static SnippetsView instance;
	private static File snippetsRootFolder;

	public static SnippetsView getInstance() {
		return instance;
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;

		this.viewArea = viewArea;

		viewArea.setLayout(new GridLayout(1, false));

		URL fileURL;
		try {
			fileURL = new URL("platform:/plugin/pa.iscde.snippets/Snippets");
			snippetsRootFolder = new File(FileLocator.resolve(fileURL)
					.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		createExplorer();
	}

	public File getSnippetsRootFolder() {
		return snippetsRootFolder;
	}

	protected void createExplorer() {
		if (SnippetsExplorer.getInstance() == null)
			new SnippetsExplorer(viewArea, SWT.NONE);
		else
			SnippetsExplorer.getInstance().hideUnhide();
		viewArea.layout();
	}

	// With file
	protected void createSnippetCode(File snp) {
		new SnippetCode(snp, viewArea, SWT.NONE);
		viewArea.layout();
	}

	// Without file
	protected void createSnippetCode() {
		new SnippetCode(viewArea, SWT.NONE);
		viewArea.layout();
	}
}
