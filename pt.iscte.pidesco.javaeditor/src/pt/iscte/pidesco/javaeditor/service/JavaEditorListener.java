package pt.iscte.pidesco.javaeditor.service;

import java.io.File;

/**
 * Represents a listener for events in the Java Editor
 */
public interface JavaEditorListener {

	/**
	 * File open event.
	 * @param file (non-null) reference to the opened file
	 */
	void fileOpened(File file);

	/**
	 * File save event.
	 * @param file (non-null) reference to the saved file
	 */
	void fileSaved(File file);

	/**
	 * File close event.
	 * @param file (non-null) reference to the closed file
	 */
	void fileClosed(File file);

	/**
	 * File selection changed event.
	 * @param file (non-null) reference to the file
	 * @param text selected text
	 * @param offset offset of the selection (index of the first selected character)
	 * @param length length of the selection
	 */
	void selectionChanged(File file, String text, int offset, int length);

	/**
	 * Listener adapter that for each event does nothing.
	 */
	public class Adapter implements JavaEditorListener {

		@Override
		public void fileOpened(File file) {

		}

		@Override
		public void fileSaved(File file) {

		}

		@Override
		public void fileClosed(File file) {

		}

		@Override
		public void selectionChanged(File file, String text, int offset, int length) {

		}
	}
}
