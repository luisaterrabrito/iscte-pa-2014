package pt.iscte.pidesco.projectbrowser.internal;

import java.io.File;

public interface Filter {

	/**
	 * Filter to determine if a file should be included in the scan
	 * @param fileName non-null reference to the file
	 * @return true if the file is to be included in the scan, false otherwise
	 */
	boolean accept(File file);
}
