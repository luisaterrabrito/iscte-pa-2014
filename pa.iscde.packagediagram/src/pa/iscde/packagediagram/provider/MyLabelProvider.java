package pa.iscde.packagediagram.provider;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

import pa.iscde.packagediagram.figure.DrawFigure;
import pa.iscde.packagediagram.internal.PackageDiagramView.ChangeColor;
import pa.iscde.packagediagram.model.ConnectionModel;
import pa.iscde.packagediagram.model.NodeModel;

public class MyLabelProvider extends LabelProvider implements IFigureProvider,
		IEntityConnectionStyleProvider {

	private ChangeColor colorsMap;

	public MyLabelProvider(ChangeColor colorsMap) {
		this.colorsMap = colorsMap;
	}
	
	/**
	 * Get the text: nodeModel, connectionModel
	 * 
	 * @param element
	 * @return text of the view
	 */
	public String getText(Object element) {
		if (element instanceof NodeModel) {
			NodeModel NodeModel = (NodeModel) element;
			return NodeModel.getName();
		}

		if (element instanceof ConnectionModel) {
			ConnectionModel ConnectionModel = (ConnectionModel) element;
			return ConnectionModel.getLabel();
		}

		if (element instanceof EntityConnectionData) {
			return "<<import>>";
		}
		throw new RuntimeException("Wrong type: "
				+ element.getClass().toString());
	}



	@Override
	public IFigure getFigure(Object element) {

		if (element instanceof NodeModel) {
			NodeModel node = (NodeModel) element;

			DrawFigure nodeFigure = new DrawFigure(node.getName());
			nodeFigure.setForegroundColor(colorsMap.getForeground(node.getName()));
			nodeFigure.setBackgroundColor(colorsMap.getBackground(node.getName()));
			nodeFigure.setSize(nodeFigure.getPreferredSize());

			return nodeFigure;
		}
		return null;
	}

	@Override
	public Color getColor(Object arg0, Object arg1) {
	
		return null;
	}

	@Override
	public int getConnectionStyle(Object source, Object destination) {

		return ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DASH;
	}

	@Override
	public Color getHighlightColor(Object arg0, Object arg1) {
	
		return null;
	}

	@Override
	public int getLineWidth(Object arg0, Object arg1) {
	
		return 5;
	}

	@Override
	public IFigure getTooltip(Object arg0) {
		return null;
	}

}
