package pa.iscde.perspectives.internal;

import java.io.File;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.perspectives.services.PerspectiveServices;

public class PerpectivesActivator implements BundleActivator
{

	private static BundleContext	context;

	static BundleContext getContext()
	{
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception
	{
		PerpectivesActivator.context = bundleContext;
		PerspectiveServicesImpl service = new PerspectiveServicesImpl(bundleContext);
		context.registerService(PerspectiveServices.class, service, null);
		File pFolder = service.getPerspectiveFolder();
		if (!pFolder.exists())
			pFolder.mkdir();
	}
	public void stop(BundleContext bundleContext)
			throws Exception
	{
		PerpectivesActivator.context = null;
	}

}
