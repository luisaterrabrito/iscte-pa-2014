package pa.iscde.metrics.mlcst;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.commands.services.Command;
import pa.iscde.metrics.extensibility.MetricsService;

public class RefreshCommand implements Command{

	private MetricsService services;

	@Override
	public void action() {
		Bundle bundle = FrameworkUtil.getBundle(MetricsService.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<MetricsService> reference = context
				.getServiceReference(MetricsService.class);
		services = context.getService(reference);
		
		services.refresh();
	}

}
