package pt.iscte.pidesco.javaeditor;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoTool;
import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;
import pt.iscte.pidesco.javaeditor.service.JavaEditorFileListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class PackageFocusTool implements PidescoTool {

	private static final String FILTER_ID = "pt.iscte.pidesco.javaeditor.packagefocus";

	private ProjectBrowserServices browser;

	public PackageFocusTool() {
		BundleContext context = JavaEditorActivator.getInstance().getContext();
		ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		browser = context.getService(ref);
	}

	@Override
	public void execute(boolean selected) {
		if(selected) {
			browser.activateFilter(FILTER_ID);
			JavaEditorActivator.getInstance().addListener(
					new JavaEditorFileListener.Adapter() {
						@Override
						public void fileOpened(File file) {
							browser.refreshTree();
						}
					});
		}
		else {
			browser.deactivateFilter(FILTER_ID);
		}
	}
}
