package pa.iscde.perspectives.internal.ui;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import pa.iscde.perspectives.model.Perspective;

public class PerspectiveDoubleClickListener implements Listener
{

	@Override
	public void handleEvent(Event event)
	{
		Point pt = new Point(event.x, event.y);
		TableItem item = ((Table) event.widget).getItem(pt);
		if (item != null)
		{
			Perspective p = (Perspective) item.getData();
			if (p.isValid())
				PerspectiveSwitcherView.getPerspectiveServices().changePerspective(p);
			else
				PerspectiveSwitcherView.getInstance().showWarning("Warning", "This perspective cannot be loaded!");
		}
	}
}
