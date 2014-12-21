package pa.iscde.metrics.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import pa.iscde.metrics.extensibility.MetricsService;

public class Activator implements BundleActivator {

	private MetricsServiceImpl services;

	@Override
	public void start(BundleContext context) throws Exception {
		services = new MetricsServiceImpl();
		context.registerService(MetricsService.class, services, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
