/*******************************************************************************
 * Copyright (c) 2014 Andre L. Santos.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Andre L Santos - developer
 ******************************************************************************/
package pt.iscte.pidesco.javaeditor.internal;


//import static org.eclipse.ui.texteditor.AbstractDecoratedTextEditor.PRINT_MARGIN;
//import static org.eclipse.ui.texteditor.AbstractDecoratedTextEditor.PRINT_MARGIN_COLOR;
//import static org.eclipse.ui.texteditor.AbstractDecoratedTextEditor.PRINT_MARGIN_COLUMN;

import java.io.File;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IRegion;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class SimpleJavaEditor extends TextEditor {

	public static final String EDITOR_ID = "pt.iscte.pidesco.javaeditor";

	private JavaEditorServices services;

	public SimpleJavaEditor() {
		setSourceViewerConfiguration(new Configuration());
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		window.getActivePage().addPartListener(JavaEditorActivator.getInstance());
		showOverviewRuler();
		services = JavaEditorActivator.getInstance().getServices();
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {
		ISourceViewer sourceViewer=  super.createSourceViewer(parent, ruler, styles);

		fSourceViewerDecorationSupport= new SourceViewerDecorationSupport(sourceViewer, fOverviewRuler, fAnnotationAccess, getSharedColors());
		super.configureSourceViewerDecorationSupport(fSourceViewerDecorationSupport);
		return sourceViewer;
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		compile();
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		compile();
	}

	@Override
	protected void editorSaved() {
		super.editorSaved();
		JavaEditorActivator.getInstance().notitySavedFile(getFile());
	}

	private void compile() {
		clearAnnotations();
		File f = getFile();
		for(IProblem p : services.parseFile(f, new ASTVisitor(){})) {
			int offset = p.getSourceStart();
			int length = p.getSourceEnd() - p.getSourceStart() + 1;
			services.addAnnotation(f, AnnotationType.ERROR, p.getMessage(), offset, length);
		}
	}

	private File getFile() {
		FileStoreEditorInput fs = (FileStoreEditorInput) getEditorInput();
		File f = new File(fs.getURI().getPath());
		return f;
	}

	private void clearAnnotations() {
		IAnnotationModel amodel = getDocumentProvider().getAnnotationModel(getEditorInput());
		Iterator it = amodel.getAnnotationIterator();
		while(it.hasNext()) {
			Annotation a = (Annotation) it.next();
			amodel.removeAnnotation(a);
		}
	}
}
