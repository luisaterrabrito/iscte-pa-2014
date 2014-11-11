package pt.iscte.pidesco.javaeditor;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditor;
import pt.iscte.pidesco.projectbrowser.extensibility.ProjectBrowserFilter;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class PackageFocusFilter implements ProjectBrowserFilter {

	@Override
	public boolean include(SourceElement element, PackageElement parent) {
		File f = JavaEditor.getOpenFile();
		if(element.isPackage() && ((PackageElement) element).hasChild(f, true))
			return true;
		else if (element.isClass() && element.getParent().hasChild(f, false))
			return true;
//		else
//			return element.getFile().equals(f);
		return false;
	}

}
