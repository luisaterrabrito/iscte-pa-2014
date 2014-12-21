package pa.iscde.metrics.internal.metrics;

import pa.iscde.metrics.extensibility.DefaultVisitor;
import pa.iscde.metrics.extensibility.Metricable;

public class LineTypeRatio implements Metricable {

	public double calculateMetric(DefaultVisitor dv) {
		return (double)dv.getLogicalLineCounter()/dv.getPhysicalLineCounter();
	}

}
