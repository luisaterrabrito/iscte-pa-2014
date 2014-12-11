package pa.iscde.conventions.integrationpoints;

import org.eclipse.zest.layouts.LayoutAlgorithm;



import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

import pa.iscde.callgraph.extensibility.CustomLayout;

public class IntegrationPointCustomLayout implements CustomLayout{

	@Override
	public LayoutAlgorithm getCustomLayout() {

		VerticalLayoutAlgorithm draw = new VerticalLayoutAlgorithm();
		draw.setEntityAspectRatio(20.0);
		draw.setStyle(4);
		return draw;
	}

}