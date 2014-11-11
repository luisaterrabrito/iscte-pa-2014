package pt.iscte.pidesco.projectbrowser.service;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public interface ProjectBrowserListener {

	void doubleClick(SourceElement element);
	
	void selectionChanged(Iterable<SourceElement> selection);
	
	
}
