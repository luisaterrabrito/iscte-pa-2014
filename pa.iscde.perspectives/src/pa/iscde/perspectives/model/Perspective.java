package pa.iscde.perspectives.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.graphics.Image;
import pa.iscde.perspectives.extensibility.PerspectiveListener;
import pa.iscde.perspectives.internal.ui.PerspectiveSwitcherView;
import pt.iscte.pidesco.extensibility.ViewLocation;

public class Perspective implements Serializable
{
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perspective other = (Perspective) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
	private static final long	serialVersionUID	= 2376147722388607017L;
	private transient Image		icon;
	private String				id;
	private String				sourcePlugin;
	private String				name;
	private PerspectiveListener	listener;
	private List<View>			parentViews;
	private String				iconPath;

	public Perspective(String id, String sourcePluing, String name, String iconPath, PerspectiveListener listener, List<View> parentViews)
	{
		this.id = id;
		this.sourcePlugin = sourcePluing;
		this.name = name;
		this.listener = listener;
		this.iconPath = iconPath;
		this.parentViews = new ArrayList<>(parentViews);
		if (iconPath != null)
			this.icon = PerspectiveSwitcherView.getPidescoServices().getImageFromPlugin(sourcePluing, iconPath);
	}
	public String getId()
	{
		return id;
	}
	public String getSourcePlugin()
	{
		return sourcePlugin;
	}
	public String getName()
	{
		return name;
	}
	public PerspectiveListener getListener()
	{
		return listener;
	}
	public Image getIcon()
	{
		return icon;
	}
	public String getIconPath()
	{
		return iconPath;
	}
	public List<View> getParentViews()
	{
		return parentViews;
	}
	public boolean isValid()
	{
		for (View view : parentViews)
			if (!view.isValid())
				return false;
		return true;
	}

	public List<ViewLocation> getViewLocations()
	{
		List<ViewLocation> toReturn = new ArrayList<ViewLocation>();
		for (View view : parentViews)
		{
			toReturn.addAll(view.getViewLocations());
		}
		return toReturn;
	}
	@Override
	public String toString()
	{
		return getName() + " (source:  " + getId() + ")";
	}
}
