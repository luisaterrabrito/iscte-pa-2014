package pa.iscde.perspectives.internal.ui;

import pt.iscte.pidesco.extensibility.PidescoTool;

public class RefreshPerspectivesTool implements PidescoTool
{

	@Override
	public void run(boolean activate)
	{
		PerspectiveSwitcherView.getPerspectiveServices().refreshAvailablePerspectives();
	}

}
