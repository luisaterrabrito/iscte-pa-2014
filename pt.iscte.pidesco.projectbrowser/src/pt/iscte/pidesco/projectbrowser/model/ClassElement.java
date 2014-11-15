package pt.iscte.pidesco.projectbrowser.model;

import java.io.File;

/**
 * Represents a class as a source element.
 */
public final class ClassElement extends SourceElement {
	
	public ClassElement(PackageElement parent, File file) {
		super(parent, file.getName(), file);
		if(parent == null)
			throw new NullPointerException("A class source element must have a parent (package)");
	}
	
}