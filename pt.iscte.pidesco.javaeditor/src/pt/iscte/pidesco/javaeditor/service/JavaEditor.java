package pt.iscte.pidesco.javaeditor.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringBufferInputStream;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IRegion;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;

public class JavaEditor {

	public static File getOpenFile() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorInput input = page.getActiveEditor().getEditorInput();
		if(input instanceof FileStoreEditorInput) {
			String path = ((FileStoreEditorInput) input).getURI().getPath();
			return new File(path);
		}
		return null;
	}


	public static boolean openFile(File file) {
		if(file.exists() && file.isFile()) {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				IDE.openEditorOnFileStore(page, fileStore);
				JavaEditorActivator.getInstance().notityOpenFile(file);
				return true;
			} catch (PartInitException e) {
				return false;
			}
		} 
		else {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "File not found");
			return false;
		}
	}

	public static void selectText(File file, int offset, int length) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
		ITextEditor editor = null;
		try {
			editor = (ITextEditor) IDE.openEditorOnFileStore(page, fileStore);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		editor.selectAndReveal(offset, length);
	}

	public static int getSelection() {
		return 0;
	}

	public static IProblem[] parseFile(File file, ASTVisitor visitor) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		Map options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
		parser.setCompilerOptions(options);
		String src = readSource(file);
		parser.setSource(src.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		cu.accept(visitor);
		return cu.getProblems();
	}


	private static String readSource(File file) {
		StringBuilder src = new StringBuilder();
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine())
				src.append(scanner.nextLine()).append('\n');
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return src.toString();
	}

	public enum CodeAnnotationType {
		ERROR("org.eclipse.ui.workbench.texteditor.error"),
		WARNING("org.eclipse.ui.workbench.texteditor.warning"),
		INFO("org.eclipse.ui.workbench.texteditor.info");

		private String id;

		private CodeAnnotationType(String id) {
			this.id = id;
		}
	}

	public static void addCodeAnnotation(File file, CodeAnnotationType type, String text, int offset, int length) {
		if(openFile(file)) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart part = page.getActiveEditor();
			IEditorInput input = part.getEditorInput();
			IAnnotationModel amodel = ((ITextEditor) part).getDocumentProvider().getAnnotationModel(input);
			Annotation ann = new Annotation(false);
			ann.setText(text);
			ann.setType(type.id);
			amodel.addAnnotation(ann, new Position(offset, length));
		}
	}



}
