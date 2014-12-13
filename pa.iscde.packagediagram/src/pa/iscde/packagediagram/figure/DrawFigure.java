package pa.iscde.packagediagram.figure;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;

/**
 * Draw box, text and location nodes
 */

public class DrawFigure extends PolygonShape {
	
	private String name;
	
	public DrawFigure(String name) {
		this.name = name;
		final GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 10 + NodeBorder.HEIGHT;
		gridLayout.marginWidth = 10;
		setLayoutManager(gridLayout);

		Label noteLabel = new Label(name);
		add(noteLabel);

		//TODO problem: size 1000?
		Rectangle r = new Rectangle(0, 0, 1000, 1000);
		setStart(r.getTopLeft());
		addPoint(r.getTopLeft());
		addPoint(r.getTopLeft().getTranslated(NodeBorder.WIDTH, 0));
		addPoint(r.getTopLeft().getTranslated(NodeBorder.WIDTH, NodeBorder.HEIGHT));
		addPoint(r.getTopRight().getTranslated(0, NodeBorder.HEIGHT));
		addPoint(r.getBottomRight());
		addPoint(r.getBottomLeft());
		addPoint(r.getTopLeft());
		setEnd(r.getTopLeft());
		setFill(true);

		setBorder(new NodeBorder());

		new FigureMover(this);
	}

	public String getName() {
		return name;
	}
	

}
