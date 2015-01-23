package pa.iscde.perspectives.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import pa.iscde.perspectives.extensibility.PerspectiveListener;
import pa.iscde.perspectives.internal.ui.PerspectiveSwitcherView;
import pa.iscde.perspectives.model.Perspective;
import pa.iscde.perspectives.model.View;
import pa.iscde.perspectives.services.PerspectiveServices;
import pt.iscte.pidesco.extensibility.PidescoExtensionPoint;
import pt.iscte.pidesco.extensibility.ViewLocation;
import pt.iscte.pidesco.extensibility.ViewLocation.Position;

public class PerspectiveServicesImpl implements PerspectiveServices
{
	private static final String		PERSPECTIVES_EXTENSION			= ".per";
	private static final String		PERSPECTIVES_FOLDER				= "perspectives";
	private static final boolean	USE_PERSPECTIVE_FOLDER_LOCAL	= true;
	private static final String		PERSPECTIVE_EXTENSION_POINT		= "pa.iscde.perspectives.perspective";
	private List<Perspective>		availablePerspectives;
	private List<String>			pidescoViews;
	private Perspective				currentPerspective;
	private BundleContext			context;

	public PerspectiveServicesImpl(BundleContext context)
	{
		this.context = context;
	}
	@Override
	public Perspective getCurrentPerspective()
	{
		return currentPerspective;
	}
	@Override
	public List<Perspective> getAvailablePerspectives()
	{
		if (availablePerspectives == null)
			refreshAvailablePerspectives();
		return new ArrayList<>(availablePerspectives);
	}
	@Override
	public void refreshAvailablePerspectives()
	{
		IExtension[] perspectiveExtensions = Platform.getExtensionRegistry().
				getExtensionPoint(PERSPECTIVE_EXTENSION_POINT).getExtensions();
		availablePerspectives = loadFromExtensions(perspectiveExtensions);
		PerspectiveSwitcherView.getInstance().forcePerspectiveListRefresh();
	}
	@Override
	public void refreshPidescoViews()
	{
		pidescoViews = new ArrayList<String>();
		for (IExtension viewExtension : PidescoExtensionPoint.VIEW.getExtensions())
			pidescoViews.add(viewExtension.getUniqueIdentifier());
	}
	@Override
	public List<String> getLoadedPidescoViews()
	{
		if (pidescoViews == null)
			refreshPidescoViews();
		return new ArrayList<>(pidescoViews);
	}
	@Override
	public void changePerspective(Perspective p)
	{
		if (!availablePerspectives.contains(p))
			return;
		if (currentPerspective != null && currentPerspective.getListener() != null)
			currentPerspective.getListener().onPerspectiveDeactivation();
		if (p != null && p.getListener() != null)
			p.getListener().onPerspectiveActivation();

		// Change perspective
		List<ViewLocation> locations = p.getViewLocations();
		PerspectiveSwitcherView.getPidescoServices().layout(locations);

		if (currentPerspective != null && currentPerspective.getListener() != null)
			currentPerspective.getListener().afterPerspectiveDeactivation();
		if (p != null && p.getListener() != null)
			p.getListener().afterPerspectiveActivation();
		// change current perspective
		currentPerspective = p;
		this.refreshAvailablePerspectives();
	}
	/**
	 * Used to get File where perspectives are stored
	 * 
	 * @return
	 * @throws IOException
	 */
	protected File getPerspectiveFolder() throws IOException
	{
		if (USE_PERSPECTIVE_FOLDER_LOCAL)
			return new File(FileLocator.toFileURL(context.getBundle().getResource(PERSPECTIVES_FOLDER)).getPath());
		else
			return context.getDataFile(PERSPECTIVES_FOLDER);
	}
	/**
	 * Auxiliary method to load extensions
	 * 
	 * @param extensions
	 * @return
	 */
	protected static List<Perspective> loadFromExtensions(IExtension[] extensions)
	{
		List<Perspective> perspectivesList = new ArrayList<Perspective>();
		// iterate all extensions
		for (IExtension extension : extensions)
		{
			String sourcePluing = extension.getContributor().getName();
			String extensionId = extension.getUniqueIdentifier();
			IConfigurationElement[] perspectives = extension.getConfigurationElements();
			// iterate all perspectives in one extension
			for (IConfigurationElement perspective : perspectives)
			{
				String perspectiveName = perspective.getAttribute("name");
				String perspectiveId = extensionId + "." + perspective.getAttribute("id");
				String perspectiveIcon = perspective.getAttribute("icon");
				PerspectiveListener listener = null;
				try
				{
					listener = (PerspectiveListener) perspective.createExecutableExtension("listener");
				}
				catch (CoreException e)
				{
				}
				Perspective p = new Perspective(perspectiveId, sourcePluing, perspectiveName, perspectiveIcon, listener,
						getChildrenViews(perspective));
				perspectivesList.add(p);
			}
		}
		return perspectivesList;
	}
	private static List<View> getChildrenViews(IConfigurationElement parent)
	{
		List<View> childrenViews = new ArrayList<>();
		for (IConfigurationElement child : parent.getChildren())
		{
			String viewId = child.getAttribute("view_id");
			Float scaleFactor = Float.parseFloat(child.getAttribute("scale_factor"));
			Position position = Position.valueOf(child.getAttribute("postition"));
			View view = new View(viewId, scaleFactor, position, getChildrenViews(child));
			childrenViews.add(view);
		}
		return childrenViews;
	}
	@Override
	public Perspective getPerspectiveFromId(String id)
	{
		for (Perspective perspective : getAvailablePerspectives())
		{
			if (perspective.getId().equals(id))
				return perspective;
		}
		return null;
	}
	@Override
	public boolean isCurrentPerspective(Perspective p)
	{
		if (currentPerspective == null)
			return false;
		else
			return currentPerspective.equals(p);
	}
}
