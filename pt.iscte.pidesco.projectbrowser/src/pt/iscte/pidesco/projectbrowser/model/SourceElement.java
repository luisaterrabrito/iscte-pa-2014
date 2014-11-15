package pt.iscte.pidesco.projectbrowser.model;

import java.io.File;

import org.eclipse.core.runtime.Assert;

/**
 * Represents a source element that is associated with a file (class) or directory (package).
 */
public abstract class SourceElement  {
	private final PackageElement parent;
	private final String name;
	private final File file;

	SourceElement(PackageElement parent, String name, File file) {
		Assert.isNotNull(name, "name cannot be null");
		Assert.isNotNull(file, "file cannot be null");
		Assert.isTrue(file.exists(), "file does not exist");
		
		this.parent = parent;
		this.name = name;
		this.file = file;

		if(parent != null)
			parent.addChild(this);
	}

	/**
	 * Element name.
	 * @return (non-null) string 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Element file.
	 * @return (non-null) reference to the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Is this element the root (implicitly package)?
	 * @return true if yes, false otherwise
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * Is this element a package?
	 * @return true if yes, false otherwise
	 */
	public boolean isPackage() {
		return this instanceof PackageElement;
	}

	/**
	 * Is this element a class?
	 * @return true if yes, false otherwise
	 */
	public boolean isClass() {
		return this instanceof ClassElement;
	}

	/**
	 * Parent package.
	 * @return the package that contains this element, or null if this element is the root.
	 */
	public PackageElement getParent() {
		return parent;
	}

	public String toString() {
		return getName();
	}


	/**	
	 * Two source elements are equal if they have the same file (i.e. same path of the file).
	 */
	@Override
	public final boolean equals(Object obj) {
		return obj instanceof SourceElement && ((SourceElement) obj).file.equals(file);
	}

	/**
	 * The hashcode of a source element is based on the absolute path of its file.
	 */
	@Override
	public final int hashCode() {
		return file.getAbsolutePath().hashCode();
	}

}