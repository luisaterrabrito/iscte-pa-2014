package pa.iscde.umldiagram.drbps;

import org.eclipse.draw2d.MouseListener;
import org.eclipse.zest.core.widgets.Graph;





public interface ClickOption {

	/**
	 * choose what happens in double click option to each graph node
	 * ATENTION:
	 * @return org.eclipse.draw2d.MouseListener() 
	 */
	public MouseListener getAction();
	
}
