
package pa.iscde.umldiagram;


import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.graphics.Color;
import org.eclipse.zest.core.widgets.CGraphNode;

public class UMLClassFigure extends Figure {
	
	private final String name;

	class CompartmentFigure extends Figure {

		public CompartmentFigure() {
			ToolbarLayout layout = new ToolbarLayout();
			layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
			layout.setStretchMinorAxis(false);
			layout.setSpacing(2);
			setLayoutManager(layout);
			setBorder(new CompartmentFigureBorder());
		}
	}
	
	private static class CompartmentFigureBorder extends AbstractBorder {
		public Insets getInsets(IFigure figure) {
			return new Insets(1, 0, 0, 0);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(), tempRect.getTopRight());
		}
	}
		

	private CompartmentFigure methodsCompartment;
	private CGraphNode node;

	
	

	protected UMLClassFigure(String n) {
		name=n;
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		setBackgroundColor(WHITE);
		setBorder(new LineBorder(ColorConstants.black, 1));
		setOpaque(true);

		addNameLabel(n);

		methodsCompartment = new CompartmentFigure();
		add(methodsCompartment);

		setSize();

	}
	
	public String getName() {
		return name;
	}



	public static final Color GRAY = new Color(null, 230, 230, 230);
	public static Color WHITE = new Color(null, 255, 255, 255);
	

	public void select() {
		setBackgroundColor(GRAY);
	}
	
	public void unselect() {
		setBackgroundColor(WHITE);
	}
	
	private void setSize() {
		setSize(-1, -1);
	}


	public void addNameLabel(String name) {
		Label nameLabel = new Label(name);
		nameLabel.setBorder(new MarginBorder(3,10,3,10));
		nameLabel.setBackgroundColor(WHITE);
		add(nameLabel);
	}
	
	public void addNameField(String name) {
		Label nameLabel = new Label(name);
		nameLabel.setBorder(new MarginBorder(3,10,3,10));
		nameLabel.setBackgroundColor(WHITE);
		add(nameLabel);
	}
	
	public void addNameMethod(String name) {
		Label nameLabel = new Label(name);
		nameLabel.setBorder(new MarginBorder(3,10,3,10));
		nameLabel.setBackgroundColor(WHITE);
		add(nameLabel);
	}

	public void drawLine(){
		methodsCompartment = new CompartmentFigure();
		add(methodsCompartment);
	}

	public void setNode(CGraphNode node) {
		this.node=node;
		
	}

	public CGraphNode getNode() {
		return node;
	}
	
	

}