package pt.iscte.pidesco.javaeditor.service;

import java.io.File;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.text.ITextSelection;

/**
 * Services offered by the Java Editor component.
 */
public interface JavaEditorServices {

	/**
	 * Get the file that is opened (on top) in the editor.
	 * @return a reference to the opened file, or null if no file is opened
	 */
	File getOpenedFile();

	/**
	 * Opens a file in the editor.
	 * @param file (non-null) file to open
	 * @return
	 */
	void openFile(File file);
	
	
	/**
	 * Saves the state of the current file
	 * @param file (non-null) file to open
	 * @return
	 */
	
	void saveFile(File file);
	
	
	/**
	 * Sets the text of a file in the editor.
	 * @param file (non-null) file to open
	 * @param text to insert on file
	 * @return
	 */
	
	void setText(File file, String text);
	
	/**
	 * Get the selected text of a file in the editor.
	 * @param file (non-null) file to open
	 * @return an instance of ITextSelection
	 */
	
	ITextSelection getTextSelected(File file);
	

	/**
	 * Selects text in the editor.
	 * @param file (non-null) file where to select text
	 * @param offset offset of the selection (index of the first character)
	 * @param length length of the selection
	 */
	void selectText(File file, int offset, int length);

	/**
	 * Inserts a line in the editor.
	 * @param file (non-null) file where to insert the line
	 * @param text (non-null) line text
	 * @param line index of the line where to insert the new line
	 */
	void insertLine(File file, String text, int line);
	
	/**
	 * Inserts text in the editor at a certain location.
	 * @param file (non-null) file where to insert the text
	 * @param text (non-null) text to insert
	 * @param offset offset of the insertion (index of the first character)
	 * @param length length of the selection (zero if no text is to be replaced)
	 */
	void insertText(File file, String text, int offset, int length);
	
	/**
	 * Inserts text in the cursor location of the opened editor.
	 * @param text text to insert
	 */
	void insertTextAtCursor(String text);
	
	/**
	 * Returns the cursor position in editor (offset)
	 * Requires having an open editor.
	 * @return an integer equal or greater than zero
	 */
	int getCursorPosition();
	
	/**
	 * Parses a Java file using a AST visitor.
	 * @param file (non-null) file to parse
	 * @param visitor (non-null) visitor
	 * @return (non-null) an array of problems with the parsing (zero-length if none)
	 */
	IProblem[] parseFile(File file, ASTVisitor visitor);

	/**
	 * Adds an annotation to a file.
	 * @param file (non-null) file where to add the annotation
	 * @param type (non-null) annotation type (error, warning, or information)
	 * @param text (non-null) annotation text
	 * @param offset offset of the text related to the annotation 
	 * @param length selection of the text related to the annotation 
	 */
	void addAnnotation(File file, AnnotationType type, String text, int offset, int length);

	/**
	 * Adds a Java Editor listener. If the listener already exists, does nothing.
	 * @param listener (non-null) reference to a listener.
	 */
	void addListener(JavaEditorListener listener);

	/**
	 * Removes a Java Editor listener. If the listener is not registered, does nothing.
	 * @param listener (non-null) reference to a listener.
	 */
	void removeListener(JavaEditorListener listener);
}