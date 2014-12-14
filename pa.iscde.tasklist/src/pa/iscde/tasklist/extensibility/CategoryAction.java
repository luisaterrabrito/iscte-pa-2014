package pa.iscde.tasklist.extensibility;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public interface CategoryAction {
	
	/**
	 * @param ...
	 * 
	 * The method used to implement the desired action.
	 */
	public void executeAction(File file, JavaEditorServices javaServices);
	
}
