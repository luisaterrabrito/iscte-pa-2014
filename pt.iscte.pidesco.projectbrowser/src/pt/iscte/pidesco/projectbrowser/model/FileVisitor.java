package pt.iscte.pidesco.projectbrowser.model;

public interface FileVisitor {

	boolean visitPackage(PackageElement p);
	
	boolean visitClass(ClassElement c);
}
