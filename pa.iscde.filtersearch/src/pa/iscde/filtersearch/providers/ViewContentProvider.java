package pa.iscde.filtersearch.providers;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import pa.iscde.filtersearch.view.SearchCategory;

/**
	 * Classe onde são definidos os elementos que vão aparecer nos resultados
	 * 
	 * @authors LuisMurilhas & DavidAlmas
	 */
	public class ViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object input) {
			List<SearchCategory> categories = (List<SearchCategory>) input;
			return categories.toArray();

		}

		public Object getParent(Object child) {
			// SearchCategory cat = (SearchCategory) child;
			return null;
		}

		public Object[] getChildren(Object parent) {
			SearchCategory cat = (SearchCategory) parent;
			return cat.getHits().toArray();
		}

		public boolean hasChildren(Object parent) {
			if(!(parent instanceof SearchCategory))
				return false;

			SearchCategory cat = (SearchCategory) parent;
			return !cat.getHits().isEmpty();
		}

		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}
	}