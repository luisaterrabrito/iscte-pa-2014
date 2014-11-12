package pt.iscte.pidesco.projectbrowser.model;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class PackageElement extends SourceElement implements Iterable<SourceElement> {
	private final SortedSet<SourceElement> children;

	public PackageElement(PackageElement parent, String name, File file) {
		super(parent, name, file);
		children = new TreeSet<SourceElement>(new Comparator<SourceElement>() {

			@Override
			public int compare(SourceElement a, SourceElement b) {
				if(a.isPackage() && b.isClass())
					return -1;
				else if(a.isClass() && b.isPackage())
					return 1;
				else
					return a.getName().compareTo(b.getName());
			}
			
		});
	}

	void addChild(SourceElement child) {
		children.add(child);
	}

	public SortedSet<SourceElement> getChildren() {
		return Collections.unmodifiableSortedSet(children);
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}

	public boolean isChildOf(PackageElement p) {
		if(getParent() == null)
			return false;
		else if(getParent().equals(p))
			return true;
		else
			return getParent().isChildOf(p);
			
	}
	public boolean hasChild(File f, boolean deepSearch) {
		for(SourceElement s : children) {
			if(s.getFile().equals(f))
				return true;
			else if(deepSearch && s.isPackage())
				return ((PackageElement) s).hasChild(f, deepSearch);
		}
		return false;
	}

	@Override
	public Iterator<SourceElement> iterator() {
		return getChildren().iterator();
	}
	
	public void traverse(FileVisitor visitor) {
		for(SourceElement e : this)
			traverse(e, visitor);
	}
	
	private static void traverse(SourceElement e, FileVisitor visitor) {
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
	
}