package pt.iscte.pidesco.javaeditor.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class JavaEditorServicesImpl implements JavaEditorServices {

	@Override
	public File getOpenedFile() {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart part = page.getActiveEditor();
		if(part != null) {
			IEditorInput input = part.getEditorInput();
			if(input instanceof FileStoreEditorInput) {
				String path = ((FileStoreEditorInput) input).getURI().getPath();
				return new File(path);
			}
		}
		return null;
	}


	@Override
	public void openFile(File file) {
		Assert.isNotNull(file, "file cannot be null");
		Assert.isTrue(file.exists(), "file does not exist");
		Assert.isTrue(!file.isDirectory(), "file is a directory");

		if(file.exists() && file.isFile()) {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				IDE.openEditorOnFileStore(page, fileStore);
				JavaEditorActivator.getInstance().notityOpenFile(file);
			} catch (PartInitException e) {
			}
		} 
		else {
			throw new RuntimeException("File not found");
		}
	}

	@Override
	public void selectText(File file, int offset, int length) {
		Assert.isNotNull(file, "file cannot be null");

		ITextEditor editor = openEditor(file);
		editor.selectAndReveal(offset, length);
	}


	private ITextEditor openEditor(File file) {
		Assert.isNotNull(file, "file cannot be null");

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
		ITextEditor editor = null;
		try {
			editor = (ITextEditor) IDE.openEditorOnFileStore(page, fileStore);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return editor;
	}

	@Override
	public void insertLine(File file, String text, int line) {
		Assert.isNotNull(file, "file cannot be null");
		Assert.isNotNull(text, "text cannot be null");

		ITextEditor editor = openEditor(file);
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());
		ISelectionProvider selProvider = editor.getSite().getSelectionProvider();
		try {
			int offset = doc.getLineInformation(line).getOffset();
			doc.replace(offset, 0, text + "\n");
			selProvider.setSelection(new TextSelection(offset + text.length(), 0));
		} 
		catch (BadLocationException e) {
			throw new RuntimeException("invalid line: " + line);
		}
	}

	@Override
	public void insertText(File file, String text, int offset, int length) {
		Assert.isNotNull(file, "file cannot be null");
		Assert.isNotNull(text, "text cannot be null");

		ITextEditor editor = openEditor(file);
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());
			
		try {
			doc.replace(offset, length, text);
		} catch (BadLocationException e) {
			throw new RuntimeException("invalid offset/length: " + offset + "/" + length + " (max offset = " + doc.getLength() + ")");
		}
	}
	
	@Override
	public void setText(File file, String text) {
		Assert.isNotNull(file, "file cannot be null");
		Assert.isNotNull(text, "text cannot be null");

		ITextEditor editor = openEditor(file);
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());
	    
		doc.set(text);
	}
	
	
	@Override
	public ITextSelection getTextSelected(File file){
		Assert.isNotNull(file, "file cannot be null");

		ITextEditor editor = openEditor(file);
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());
		
		return ((ITextSelection) editor.getSelectionProvider().getSelection());
	}
	
	
	@Override
	public void saveFile(File file){
		Assert.isNotNull(file, "file cannot be null");
		
		ITextEditor editor = openEditor(file);
		editor.doSave(new NullProgressMonitor());
	}


	@Override
	public void insertTextAtCursor(String text) {
		Assert.isNotNull(text, "text cannot be null");

		File file = getOpenedFile();
		if(file == null)
			throw new RuntimeException("no file is opened");

		ITextEditor editor = openEditor(file);
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument doc = dp.getDocument(editor.getEditorInput());
		ISelectionProvider selProvider = editor.getSite().getSelectionProvider();
		ITextSelection textSelection =  (ITextSelection) selProvider.getSelection();
		final int offset = textSelection.getOffset();
		try {
			doc.replace(offset, 0, text);
			selProvider.setSelection(new TextSelection(offset + text.length(), 0));

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCursorPosition() {
		File file = getOpenedFile();
		if(file == null)
			throw new RuntimeException("no file is opened");

		ITextEditor editor = openEditor(file);
		ISelectionProvider selProvider = editor.getSite().getSelectionProvider();
		ITextSelection textSelection =  (ITextSelection) selProvider.getSelection();
		return textSelection.getOffset();
	}



	@Override
	public IProblem[] parseFile(File file, ASTVisitor visitor) {
		Assert.isNotNull(file, "file cannot be null");
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


	private String readSource(File file) {
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



	@Override
	public void addAnnotation(File file, AnnotationType type, String text, int offset, int length) {
		Assert.isNotNull(file, "file cannot be null");

		openFile(file);
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart part = page.getActiveEditor();
		IEditorInput input = part.getEditorInput();
		IAnnotationModel amodel = ((ITextEditor) part).getDocumentProvider().getAnnotationModel(input);
		Annotation ann = new Annotation(false);
		ann.setText(text);
		ann.setType(type.ID);
		amodel.addAnnotation(ann, new Position(offset, length));
	}


	@Override
	public void addListener(JavaEditorListener listener) {
		Assert.isNotNull(listener, "argument cannot be null");
		JavaEditorActivator.getInstance().addListener(listener);
	}


	@Override
	public void removeListener(JavaEditorListener listener) {
		Assert.isNotNull(listener, "argument cannot be null");
		JavaEditorActivator.getInstance().removeListener(listener);
	}





}
