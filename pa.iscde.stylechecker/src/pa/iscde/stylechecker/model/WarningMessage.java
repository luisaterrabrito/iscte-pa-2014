package pa.iscde.stylechecker.model;

import java.io.File;

public class WarningMessage {
	
	private String warnMessage;
	private File file;
	private int position;
	private int length;
	
	/**
	 * @return the warnMessage
	 */
	public String getWarnMessage() {
		return warnMessage;
	}
	
	
	
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	
	
	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
	
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}


}
