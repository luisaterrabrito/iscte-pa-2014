package pt.iscte.pidesco.projectbrowser.model;

import java.io.File;

public  class ClassElement extends SourceElement {
	

	public ClassElement(PackageElement parent, File file) {
		super(parent, file.getName(), file);
	}
	
}