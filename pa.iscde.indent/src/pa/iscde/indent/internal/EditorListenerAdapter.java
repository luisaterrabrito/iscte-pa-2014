package pa.iscde.indent.internal;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;

public class EditorListenerAdapter extends JavaEditorListener.Adapter {
	private String selectedText = null;
	private File selectedFile = null;

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		this.selectedText = text;
	}
	
	@Override
	public void fileOpened(File file) {
		selectedFile = file;
	}

	@Override
	public void fileSaved(File file) {

	}

	@Override
	public void fileClosed(File file) {
		selectedFile = null;
	}


	public File getSelectedFile() {
		return selectedFile;
	}
	
	public String getSelectedText() {
		return selectedText;
	}

}
