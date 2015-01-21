package pa.iscde.perspectives.hmcgf.internal.extensions;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import pa.iscde.commands.services.Command;
import pa.iscde.perspectives.services.PerspectiveServices;

/**
 * Extension for plugin pa.iscde.commands.command
 * 
 * @author Henrique Ferreira hmcgf
 *
 */
public class EclipseLikePerspectiveCommand implements Command
{

	private static final String	ECLIPSE_LIKE_PERSPECTIVE_ID	= "pa.iscde.perspectives.default.eclipse-like";
	private PerspectiveServices	perspectiveServices;

	@Override
	public void action()
	{
		BundleContext context = FrameworkUtil.getBundle(PerpectiveSearchProvider.class).getBundleContext();
		perspectiveServices = context.getService(context.getServiceReference(PerspectiveServices.class));
		perspectiveServices.changePerspective(perspectiveServices.getPerspectiveFromId(ECLIPSE_LIKE_PERSPECTIVE_ID));
	}

}
