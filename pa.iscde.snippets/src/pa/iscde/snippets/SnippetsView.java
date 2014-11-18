package pa.iscde.snippets;

import java.io.File;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;

public class SnippetsView implements PidescoView {

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		
		new SnippetsExplorer(viewArea, SWT.NONE);

		File test = new File(SnippetsView.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("/", "\\") + "\\Snippets");
		System.out.println(test.getAbsolutePath());
		System.out.println(test.exists());
		File[] files = test.listFiles();
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getAbsolutePath());
		}
	}

}
