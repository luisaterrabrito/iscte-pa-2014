package pa.iscde.snippets.gui;

import java.io.File;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;

public class SnippetsView implements PidescoView {
	private Composite viewArea;
	private static SnippetsView instance;
	
	public static SnippetsView getInstance(){
		return instance;
	}
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		this.viewArea = viewArea;
		
		createExplorer();

		
		File test = new File(SnippetsView.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("/", "\\") + "\\Snippets");
		System.out.println(test.getAbsolutePath());
		System.out.println(test.exists());
		File[] files = test.listFiles();
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getAbsolutePath());
		}
	}
	
	public void createExplorer(){
		new SnippetsExplorer(viewArea, SWT.NONE);
	}
	
	// With file
	public void createSnippetCode(File snp){
		new SnippetCode(snp, viewArea, SWT.NONE);
	}

	//Without file
	public void createSnippetCode(){
		new SnippetCode(viewArea, SWT.NONE);
	}
}
