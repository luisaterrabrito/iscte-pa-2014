package pt.iscte.pidesco.internal;

import java.util.List;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.ViewLocation;

public class Perspective implements IPerspectiveFactory {


	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		List<ViewLocation> locations = PidescoActivator.getInstance().getViewLocations();

		for(ViewLocation loc : locations) {
			String view = PidescoServices.VIEW_ID + ":" + loc.getViewId();
			String relative = loc.getRelativeViewId();
			if(relative == null)
				relative = IPageLayout.ID_EDITOR_AREA;
			else
				relative = PidescoServices.VIEW_ID + ":" + relative;

//			layout.addView(view, match(loc.getPosition()), new Float(loc.getRatio()), relative);
			layout.addStandaloneView(view, true, match(loc.getPosition()), new Float(loc.getRatio()), relative);
		}
		//		List<ViewLayout> = PidescoActivator.getInstance().getLayout();
		//		playout.
	}

	private static int match(ViewLocation.Position p) {
		switch(p) {
		case TOP: return IPageLayout.TOP;
		case BOTTOM: return IPageLayout.BOTTOM;
		case LEFT: return IPageLayout.LEFT;
		case RIGHT: return IPageLayout.RIGHT;
		default: return 0;
		}
	}
}
