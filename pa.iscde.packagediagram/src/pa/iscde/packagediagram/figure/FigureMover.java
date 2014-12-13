package pa.iscde.packagediagram.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import pa.iscde.packagediagram.internal.PackageDiagramView;

public class FigureMover implements MouseListener, MouseMotionListener {

	private IFigure figure;
	private Point location;
	private static IFigure figure_select=null;

	public FigureMover(IFigure figure) {

		this.figure = figure;
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent event) {

		if(figure_select == null) {
			figure_select = figure;
			figure.setBackgroundColor(ColorConstants.white);
		}
		else if (figure_select != figure) {
			figure_select.setBackgroundColor(figure.getBackgroundColor());
			figure.setBackgroundColor(ColorConstants.white);
			figure_select = figure;
		}
		location = event.getLocation();
		
		if (event.button != 1) {

			PackageDiagramView.getInstance().loadActionMenu();
			
		}
		event.consume();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if (location == null)
			return;
		location = null;
		event.consume();

	}

	@Override
	public void mouseDoubleClicked(MouseEvent event) {
		event.consume();
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if (location == null)
			return;
		Point newLocation = event.getLocation();
		if (newLocation == null)
			return;
		Dimension offset = newLocation.getDifference(location);
		if (offset.width == 0 && offset.height == 0)
			return;
		location = newLocation;
		UpdateManager updateMgr = figure.getUpdateManager();
		LayoutManager layoutMgr = figure.getParent().getLayoutManager();
		Rectangle bounds = figure.getBounds();
		updateMgr.addDirtyRegion(figure.getParent(), bounds);
		bounds = bounds.getCopy().translate(offset.width, offset.height);
		layoutMgr.setConstraint(figure, bounds);
		figure.translate(offset.width, offset.height);
		updateMgr.addDirtyRegion(figure.getParent(), bounds);
		event.consume();

	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseHover(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub

	}

}
