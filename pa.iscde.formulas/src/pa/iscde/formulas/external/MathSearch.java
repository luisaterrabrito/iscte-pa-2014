package pa.iscde.formulas.external;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.formulas.view.FormulasView;

public class MathSearch implements SearchProvider{
	
	
	@Override
	public List<Object> getResults(String text) {
		List<Object> t = new LinkedList<Object>();
		t.add("asda");
		t.add("asdaasd");
		return t;
	}

	@Override
	public Image setImage(Object object) {
		if(object instanceof String)
			return FormulasView.getInstance().getImages().get("draw.gif");
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		
	}
	
}
