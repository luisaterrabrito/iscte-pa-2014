package extensibility;

import org.eclipse.swt.graphics.Image;

public interface IconIdentifierProvider {

	public void setIcon(Image icon);
	
	public void setIconConstraints();
	
}
