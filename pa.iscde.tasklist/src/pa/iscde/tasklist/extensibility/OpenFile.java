package pa.iscde.tasklist.extensibility;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class OpenFile implements CategoryAction {
	
	@Override
	public void executeAction(File file, JavaEditorServices javaServices) {
		javaServices.openFile(file);
	}
}