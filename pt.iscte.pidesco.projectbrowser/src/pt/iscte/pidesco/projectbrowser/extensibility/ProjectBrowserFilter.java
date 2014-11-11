package pt.iscte.pidesco.projectbrowser.extensibility;


import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public interface ProjectBrowserFilter {
	
	boolean include(SourceElement element, PackageElement parent);
}
