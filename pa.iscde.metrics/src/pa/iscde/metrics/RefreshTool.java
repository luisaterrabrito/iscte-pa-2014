package pa.iscde.metrics;
import pt.iscte.pidesco.extensibility.PidescoTool;



public class RefreshTool implements PidescoTool {

		@Override
		public void run(boolean selected) {
			MetricsView.getInstance().refresh();
		}

}
