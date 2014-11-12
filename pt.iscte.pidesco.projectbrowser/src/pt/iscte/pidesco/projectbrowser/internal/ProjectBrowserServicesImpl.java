package pt.iscte.pidesco.projectbrowser.internal;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ProjectBrowserServicesImpl implements ProjectBrowserServices {

	public PackageElement getRootPackage() {
		return ProjectBrowserView.getInstance().getRootPackage();
	}
	
	public void refreshTree() {
		ProjectBrowserView.getInstance().refresh();
	}
	
	public void activateFilter(String id) {
		ProjectBrowserView.getInstance().activateFilter(id);
	}
	
	public void deactivateFilter(String id) {
		ProjectBrowserView.getInstance().deactivateFilter(id);
	}
	
	public void addListener(ProjectBrowserListener listener) {
		ProjectBrowserActivator.getInstance().addListener(listener);
	}
	
	public void removeListener(ProjectBrowserListener listener) {
		ProjectBrowserActivator.getInstance().removeListener(listener);
	}
	
}
