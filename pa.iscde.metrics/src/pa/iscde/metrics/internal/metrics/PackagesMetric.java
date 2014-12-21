package pa.iscde.metrics.internal.metrics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pa.iscde.metrics.extensibility.DefaultVisitor;
import pa.iscde.metrics.extensibility.Metricable;
import pa.iscde.metrics.internal.MetricsView;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class PackagesMetric implements Metricable {
	
	private PackageElement root = MetricsView.getInstance().getBrowserServices().getRootPackage();
	double packageCounter;
		
	public double calculateMetric(DefaultVisitor dv) {
		
		List<SourceElement> elements = new ArrayList<SourceElement>();
		elements = MetricsView.getInstance().listElements(root);
		for (SourceElement i : elements) {
			if(i.isPackage()){
			packageCounter++;
			}
		}
		return packageCounter;
	}

}
