package pa.iscde.metrics.internal.metrics;

import pa.iscde.metrics.extensibility.Metricable;
import pa.iscde.metrics.internal.MetricsView;

public class PhysicalLinesMetric implements Metricable {

	public double calculateMetric() {
	
		return MetricsView.getInstance().getVisitor().getPhysicalLineCounter();
	}

}
