package pt.iscte.pidesco.projectbrowser.model;

import java.io.File;

import org.eclipse.core.runtime.IAdaptable;

public abstract class SourceElement implements IAdaptable {
	private final String name;
	private final PackageElement parent;
	private final File file;
	
	public SourceElement(PackageElement parent, String name, File file) {
		this.name = name;
		if(parent != null)
			parent.addChild(this);
		this.parent = parent;
		this.file = file;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public boolean isPackage() {
		return this instanceof PackageElement;
	}
	
	public boolean isClass() {
		return this instanceof ClassElement;
	}
	
//	void setParent(PackageElement parent) {
//		this.parent = parent;
//	}
	
	public PackageElement getParent() {
		return parent;
	}
	
	public String toString() {
		return getName();
	}
	
	public Object getAdapter(Class key) {
		return null;
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public final boolean equals(Object obj) {
		return obj instanceof SourceElement && ((SourceElement) obj).file.equals(file);
	}
	
	@Override
	public int hashCode() {
		return file.getAbsolutePath().hashCode();
	}

}