package pt.iscte.pidesco.javaeditor.internal;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditor;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;

public class OpenEditorListener extends ProjectBrowserListener.Adapter {

	@Override
	public void doubleClick(SourceElement element) {
		if(element instanceof ClassElement) {
			File f = ((ClassElement) element).getFile();
			JavaEditor.openFile(f);
		}
	}

}
