package pa.iscde.perspectives.internal.ui;

import pt.iscte.pidesco.extensibility.PidescoTool;

public class NewPerspectiveTool implements PidescoTool
{

	@Override
	public void run(boolean activate)
	{
		PerspectiveSwitcherView.getPerspectiveServices().createPerspective();
	}

}
