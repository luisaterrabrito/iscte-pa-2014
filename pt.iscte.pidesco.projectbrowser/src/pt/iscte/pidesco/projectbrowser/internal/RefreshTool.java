package pt.iscte.pidesco.projectbrowser.internal;


import pt.iscte.pidesco.extensibility.PidescoTool;

public class RefreshTool implements PidescoTool {

	@Override
	public void run(boolean selected) {
		ProjectBrowserView.getInstance().refresh();
	}

}
