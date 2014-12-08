package extensibility;

import org.eclipse.swt.graphics.Image;

public interface CustomIconProvider {

	public Image getEntryIcon();
	
	public boolean obeysConstraints();
	
}
