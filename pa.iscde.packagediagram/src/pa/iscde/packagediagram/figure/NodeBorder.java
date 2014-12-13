package pa.iscde.packagediagram.figure;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;

/**
 * Draw and paint nodes
 */

public class NodeBorder extends AbstractBorder {
	public static final int HEIGHT = 10;
	public static final int WIDTH = 50;
	private static final int BORDERWIDTH = 2;

	@Override
	public Insets getInsets(IFigure figure) {
		// TODO Auto-generated method stub

		return new Insets(1, 2 + HEIGHT, 2, 2); // top,left,bottom,right
	}

	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		// TODO Auto-generated method stub

		Rectangle r = figure.getBounds().getCopy();

		r.shrink(insets);

		graphics.setLineWidth(BORDERWIDTH);
		// solid long edges around border
		graphics.drawRectangle(r.x + BORDERWIDTH -1, r.y + BORDERWIDTH + HEIGHT-1,
				r.width - 2 * BORDERWIDTH +2, r.height - 2 * BORDERWIDTH - HEIGHT+2);
		graphics.drawRectangle(r.x + BORDERWIDTH -1, r.y + BORDERWIDTH-1, WIDTH,
				HEIGHT);

	}

}
