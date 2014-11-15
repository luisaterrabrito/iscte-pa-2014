package pt.iscte.pidesco.javaeditor.internal;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;

public class OpenEditorListener extends ProjectBrowserListener.Adapter {

	private JavaEditorServices services;
	
	public OpenEditorListener(JavaEditorServices services) {
		this.services = services;
	}

	@Override
	public void doubleClick(SourceElement element) {
		if(element instanceof ClassElement) {
			File f = ((ClassElement) element).getFile();
			services.openFile(f);
		}
	}

}
