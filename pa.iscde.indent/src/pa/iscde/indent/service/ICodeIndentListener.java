package pa.iscde.indent.service;

import java.io.File;

import pa.iscde.indent.model.CodeIndentOptions;

/**
 * Represents a listener for events in the CodeIndent
 */
public interface ICodeIndentListener {

	void onFormat(File file, CodeIndentOptions options);

	/**
	 * Listener adapter that for each event does nothing.
	 */
	public class Adapter implements ICodeIndentListener {
		
		
		@Override
		public void onFormat(File file, CodeIndentOptions options) {
			// TODO Auto-generated method stub
			System.out.println("Log: onFormat [].");
		}
	}
}
