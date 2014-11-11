package pt.iscte.pidesco.javaeditor.service;

import java.io.File;

public interface JavaEditorFileListener {

	void fileOpened(File file);
	
	void fileSaved(File file);
	
	void fileClosed(File file);
	
	void selectionChanged(File file, int offset, int length);
	
	public class Adapter implements JavaEditorFileListener {

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
		public void selectionChanged(File file, int offset, int length) {
			
		}
		
	}
}
