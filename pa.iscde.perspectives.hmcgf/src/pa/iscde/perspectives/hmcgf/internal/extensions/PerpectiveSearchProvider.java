package pa.iscde.perspectives.hmcgf.internal.extensions;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.perspectives.model.Perspective;
import pa.iscde.perspectives.services.PerspectiveServices;

/**
 * Extension for pa.iscde.filtersearch.SearchProvider
 * 
 * @author Henrique Ferreira
 *
 */
public class PerpectiveSearchProvider implements SearchProvider
{

	private PerspectiveServices	perspectiveServices;

	public PerpectiveSearchProvider()
	{
		BundleContext context = FrameworkUtil.getBundle(PerpectiveSearchProvider.class).getBundleContext();
		perspectiveServices = context.getService(context.getServiceReference(PerspectiveServices.class));
	}

	@Override
	public List<Object> getResults(String text)
	{
		List<Perspective> allPerspectives = perspectiveServices.getAvailablePerspectives();
		List<Object> filteredPerspectives = new ArrayList<Object>();
		for (Perspective perspective : allPerspectives)
			if (perspective.getName().contains(text) || perspective.getId().contains(text))
				filteredPerspectives.add(perspective);
		return filteredPerspectives;
	}

	@Override
	public Image setImage(Object object)
	{
		return ((Perspective) object).getIcon();
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object)
	{
		perspectiveServices.changePerspective((Perspective) object);
	}

}
