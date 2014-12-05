package pa.iscde.filtersearch.providers;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

public interface SearchProvider {
	
	List<Object> getResults(String text);
	Image setImage(Object object);
	void doubleClickAction(TreeViewer tree, Object object);
	
}
