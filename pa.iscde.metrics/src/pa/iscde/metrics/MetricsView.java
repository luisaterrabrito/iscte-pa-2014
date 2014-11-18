package pa.iscde.metrics;

import java.util.Map;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import pt.iscte.pidesco.extensibility.PidescoView;

public class MetricsView implements PidescoView{

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		Table table = new Table (viewArea, SWT.NONE);
		TableColumn metricName = new TableColumn(table, SWT.NONE);
		TableColumn metricValue = new TableColumn(table, SWT.NONE);
		
		metricName.setText("Metric name");
		metricName.setWidth(200);
		metricValue.setText("Value");
		metricValue.setWidth(50);
		
		table.setHeaderVisible(true);
		
		
	}

}
