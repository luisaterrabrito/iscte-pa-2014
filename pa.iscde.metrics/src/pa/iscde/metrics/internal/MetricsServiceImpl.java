package pa.iscde.metrics.internal;

import pa.iscde.metrics.extensibility.MetricsService;

public class MetricsServiceImpl implements MetricsService{

	public void refresh() {
		MetricsView.getInstance().refresh();
	}
	
	

}
