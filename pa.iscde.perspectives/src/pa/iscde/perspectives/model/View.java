package pa.iscde.perspectives.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pa.iscde.perspectives.internal.ui.PerspectiveSwitcherView;
import pt.iscte.pidesco.extensibility.ViewLocation;
import pt.iscte.pidesco.extensibility.ViewLocation.Position;

public class View implements Serializable
{
	private static final long	serialVersionUID	= 4423256809549593053L;
	private String				viewId;
	private float				scaleFactor;
	private Position			position;
	private List<View>			subviews;
	public View(String viewId, float scaleFactor, Position position, List<View> subviews)
	{
		this.viewId = viewId;
		this.scaleFactor = scaleFactor;
		this.position = position;
		this.subviews = new ArrayList<>(subviews);
	}
	public String getViewId()
	{
		return viewId;
	}
	public float getScaleFactor()
	{
		return scaleFactor;
	}
	public Position getPosition()
	{
		return position;
	}
	public List<View> getSubviews()
	{
		return subviews;
	}
	public boolean isValid()
	{
		for (View view : subviews)
			if (!view.isValid())
				return false;
		return PerspectiveSwitcherView.getPerspectiveServices().getLoadedPidescoViews().contains(viewId);
	}
	public List<ViewLocation> getViewLocations()
	{
		List<ViewLocation> views = new ArrayList<ViewLocation>();
		for (View child : subviews)
		{
			views.addAll(child.getViewLocations());
		}
		views.add(new ViewLocation(getViewId(), getPosition(), getScaleFactor()));
		return views;
	}
}
