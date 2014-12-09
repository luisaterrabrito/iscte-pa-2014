package pa.iscde.filtersearch.activator;
import java.io.File;
import java.util.Collection;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


public class FilterSearchActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		

		/**
		 * ProjectBrowserServices. Criar filtros e activá-los
		 */
		
		ServiceReference<ProjectBrowserServices> reference_projectBrowser = context.getServiceReference(ProjectBrowserServices.class);
		final ProjectBrowserServices projectBrowser = context.getService(reference_projectBrowser);
		projectBrowser.addListener(new ProjectBrowserListener(){

			@Override
			public void doubleClick(SourceElement element) {
				
			}

			@Override
			public void selectionChanged(Collection<SourceElement> selection) {
				
			}
		});
		
		
		
		
		/**
		 *
		 * JavaEditorServices. Abrir os ficheiros para conseguir percorre-los
		 */
		
		ServiceReference<JavaEditorServices> reference_javaEditor = context.getServiceReference(JavaEditorServices.class);
		final JavaEditorServices javaEditor = context.getService(reference_javaEditor);
		javaEditor.addListener(new JavaEditorListener() {

			@Override
			public void fileOpened(File file) {
				
			}

			@Override
			public void fileSaved(File file) {
				
			}

			@Override
			public void fileClosed(File file) {
				
			}

			@Override
			public void selectionChanged(File file, String text, int offset, int length) {
				
			}

	
		});
		
	}

	
	@Override
	public void stop(BundleContext context) throws Exception {
		
	}

}
