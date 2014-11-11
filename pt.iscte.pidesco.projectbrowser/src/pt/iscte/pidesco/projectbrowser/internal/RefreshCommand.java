package pt.iscte.pidesco.projectbrowser.internal;


import pt.iscte.pidesco.extensibility.PidescoTool;

public class RefreshCommand implements PidescoTool {

	@Override
	public void execute(boolean selected) {
		ProjectBrowserView.getInstance().refresh();
	}

}
