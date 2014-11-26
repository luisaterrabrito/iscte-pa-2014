package activator;

import java.io.File;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;

public class EditorListenerAdapter extends JavaEditorListener.Adapter {
	private String selectedText;

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		this.selectedText = text;
	}

	public String getSelectedText() {
		return selectedText;
	}

}
