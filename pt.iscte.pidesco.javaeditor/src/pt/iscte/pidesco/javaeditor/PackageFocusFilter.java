package pt.iscte.pidesco.javaeditor;

import java.io.File;

import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;
import pt.iscte.pidesco.javaeditor.internal.JavaEditorServicesImpl;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.extensibility.ProjectBrowserFilter;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class PackageFocusFilter implements ProjectBrowserFilter {

	private JavaEditorServices services;
	
	public PackageFocusFilter() {
		services = JavaEditorActivator.getInstance().getServices();
	}
	
	@Override
	public boolean include(SourceElement element, PackageElement parent) {
		File f = services.getOpenedFile();
		if(element.isPackage() && ((PackageElement) element).hasChild(f, true))
			return true;
		else if (element.isClass() && parent.hasChild(f, false))
			return true;

		return false;
	}

}
