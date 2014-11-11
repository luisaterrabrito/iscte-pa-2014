package pt.iscte.pidesco.javaeditor.internal;

import java.io.File;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.PackageDeclaration;

import pt.iscte.pidesco.javaeditor.service.JavaEditor;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;

public class OpenEditorListener implements ProjectBrowserListener {

	@Override
	public void selectionChanged(Iterable<SourceElement> selection) {

	}

	@Override
	public void doubleClick(SourceElement element) {
		if(element instanceof ClassElement) {
			File f = ((ClassElement) element).getFile();
			JavaEditor.openFile(f);
			JavaEditor.parseFile(f, new ASTVisitor() {
				@Override
				public boolean visit(PackageDeclaration node) {
					// TODO Auto-generated method stub
					System.out.println("Package: " + node.getName());
					return true;
				}
				@Override
				public boolean visit(Block node) {
					System.out.println(node);
					return true;
				}
				public boolean visit(ClassInstanceCreation node) {
					System.out.println(node);
					return true;
				}
			});
		}
	}

}
