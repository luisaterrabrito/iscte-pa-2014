package pa.iscde.filtersearch.view;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public interface SearchProvider {
	
	
	List<Object> getResults(String text);
	Image setImage(Object object);
	void doubleClickAction(Object object);
	
	
}
