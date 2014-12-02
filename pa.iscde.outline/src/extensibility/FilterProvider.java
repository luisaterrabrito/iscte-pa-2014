package extensibility;

import org.eclipse.swt.graphics.Image;

public interface FilterProvider {

	public void setIcon(Image icon);
	
	public void setFilterConstraints(); 
	
}
