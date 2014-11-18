package pa.iscde.dropcode;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.dropcode.dropreflection.DropClass;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DropCodeActivator implements BundleActivator {

	private static DropClass dropClass;

	public static DropClass getDropClass() {
		return dropClass;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		DropCodeView.getInstance();

		ServiceReference<JavaEditorServices> editorRef = context
				.getServiceReference(JavaEditorServices.class);
		final JavaEditorServices javaEditor = context.getService(editorRef);

		// ServiceReference<ProjectBrowserServices> browserRef = context
		// .getServiceReference(ProjectBrowserServices.class);
		// ProjectBrowserServices javaBrowser = context.getService(browserRef);

		javaEditor.addListener(new JavaEditorListener() {

			@Override
			public void selectionChanged(File file, String text, int offset,
					int length) {
			}

			@Override
			public void fileSaved(File file) {
			}

			@Override
			public void fileOpened(File file) {
				dropClass = new DropClass(javaEditor);
			}

			@Override
			public void fileClosed(File file) {
			}
		});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
