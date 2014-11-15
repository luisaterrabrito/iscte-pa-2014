package pt.iscte.pidesco.projectbrowser.model;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.Assert;

/**
 * Represents a package as a source element.
 * A package can be iterated as a collection of SourceElement (its children).
 */
public final class PackageElement extends SourceElement implements Iterable<SourceElement> {
	private final SortedSet<SourceElement> children;

	private static class PackageFirstSorter implements Comparator<SourceElement> {
		@Override
		public int compare(SourceElement a, SourceElement b) {
			if(a.isPackage() && b.isClass())
				return -1;
			else if(a.isClass() && b.isPackage())
				return 1;
			else
				return a.getName().compareTo(b.getName());
		}
	}
	
	/**
	 * Creates a pacakge element, given an optional parent, a name, and a file.
	 * @param parent parent package (if null, this is a root package)
	 * @param name (non-null) string representing the element name
	 * @param file (non-null) file that the element represents
	 */
	public PackageElement(PackageElement parent, String name, File file) {
		super(parent, name, file);
		children = new TreeSet<SourceElement>(new PackageFirstSorter());
	}

	void addChild(SourceElement child) {
		children.add(child);
	}

	/**
	 * Returns the package children (classes and other packages).
	 * The children are sorted according to the following:
	 * - packages appear first as the primary sorting criterion
	 * - the second sorting criterion is the alphabetical order of the chilren names
	 * @return (non-null) sorted set of child elements
	 */
	public SortedSet<SourceElement> getChildren() {
		return Collections.unmodifiableSortedSet(children);
	}

	/**
	 * Does the package has children?
	 * @return true if yes, false otherwise
	 */
	public boolean hasChildren() {
		return children.size() > 0;
	}

	/**
	 * Is this package a child (possibly indirect) of a given package?
	 * @param parent package to check if it is a parent
	 * @return true if yes, false otherwise
	 */
	public boolean isChildOf(PackageElement parent) {
		Assert.isNotNull(parent, "argument cannot be null");

		if(getParent() == null)
			return false;
		else if(getParent().equals(parent))
			return true;
		else
			return getParent().isChildOf(parent);		
	}
	
	/**
	 * Does this package contains a child associated with a given file?
	 * The search iterates over the children, optionally performing a deep search recursively.
	 * @param file file to search for
	 * @param deepSearch if true a deep search is performed, if false a shallow search is performed instead (only direct children are checked)
	 * @return true if yes, false otherwise
	 */
	public boolean hasChild(File file, boolean deepSearch) {
		Assert.isNotNull(file, "file cannot be null");
		
		for(SourceElement s : children) {
			if(s.getFile().equals(file))
				return true;
			else if(deepSearch && s.isPackage() && ((PackageElement) s).hasChild(file, deepSearch))
				return true;
		}
		return false;
	}

	@Override
	public Iterator<SourceElement> iterator() {
		return getChildren().iterator();
	}
	
	
	/**
	 * Traverses this package and its children using a 
	 * @param visitor
	 */
	public void traverse(Visitor visitor) {
		Assert.isNotNull(visitor, "argument cannot be null");
		
		for(SourceElement e : this)
			traverse(e, visitor);
	}
	
	private static void traverse(SourceElement e, Visitor visitor) {
		if(e.isClass()) {
			visitor.visitClass((ClassElement) e);
		}
		else {
			if(visitor.visitPackage((PackageElement) e)) {
				for(SourceElement c : ((PackageElement) e).getChildren())
					traverse(c, visitor);
			}
		}
	}
	
	
	/**
	 * Visitor for traversing package hierarchies.
	 */
	public interface Visitor {

		/**
		 * Invoked whenever a package is visited
		 * @param packageElement visited package
		 * @return true if the traversal should proceed to the children of the package, false otherwise
		 */
		boolean visitPackage(PackageElement packageElement);
		
		/**
		 * Invoked whenever a class is visited
		 * @param classElement visited class
		 */
		void visitClass(ClassElement classElement);
		
		/**
		 * Visitor adapter that does nothing, and always proceeds to the package children.
		 */
		public class Adapter implements Visitor {

			@Override
			public boolean visitPackage(PackageElement p) {
				return true;
			}

			@Override
			public void visitClass(ClassElement c) {
				
			}
		}
	}
	
}