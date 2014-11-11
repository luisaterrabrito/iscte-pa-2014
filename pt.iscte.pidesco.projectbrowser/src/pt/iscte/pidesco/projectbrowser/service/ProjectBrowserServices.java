package pt.iscte.pidesco.projectbrowser.service;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;

public interface ProjectBrowserServices {

	public PackageElement getRootPackage();

	public void refreshTree();

	public void activateFilter(String filterId);

	public void deactivateFilter(String filterId);

	public void addListener(ProjectBrowserListener listener);

	public void removeListener(ProjectBrowserListener listener);


}
