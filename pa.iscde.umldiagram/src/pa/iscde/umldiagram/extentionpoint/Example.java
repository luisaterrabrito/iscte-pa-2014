package pa.iscde.umldiagram.extentionpoint;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.zest.core.widgets.CGraphNode;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import pa.iscde.umldiagram.ClickOption;


/**
 * Example of the implementation of the interface ClickOption
 * @author Nuno e Diogo
 *
 */
public class Example implements ClickOption{

	@Override
	public void getAction(GraphNode nodeClicked) {
		System.out.println(nodeClicked.getText());
		
	}

}
