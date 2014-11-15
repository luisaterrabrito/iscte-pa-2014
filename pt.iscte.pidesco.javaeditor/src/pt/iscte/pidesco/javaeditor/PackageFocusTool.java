package pt.iscte.pidesco.javaeditor;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoTool;
import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class PackageFocusTool implements PidescoTool {

	private static final String FILTER_ID = "pt.iscte.pidesco.javaeditor.packagefocus";

	@Override
	public void run(boolean selected) {

		BundleContext context = JavaEditorActivator.getInstance().getContext();

		ServiceReference<PidescoServices> ref1 = context.getServiceReference(PidescoServices.class);
		final PidescoServices services = context.getService(ref1);

		ServiceReference<ProjectBrowserServices> ref2 = context.getServiceReference(ProjectBrowserServices.class);
		ProjectBrowserServices browser = context.getService(ref2);


		if(selected) {
			browser.activateFilter(FILTER_ID);
			JavaEditorActivator.getInstance().addListener(
					new JavaEditorListener.Adapter() {
						@Override
						public void fileOpened(File file) {
							services.runTool(ProjectBrowserServices.REFRESH_TOOL_ID, true);
						}
					});
		}
		else {
			browser.deactivateFilter(FILTER_ID);
		}
	}
}
