package pt.iscte.pidesco.javaeditor.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorFileListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class JavaEditorActivator implements BundleActivator, IPartListener2 {

	private static JavaEditorActivator instance;

	private BundleContext context;
	
	private ProjectBrowserListener listener = new OpenEditorListener();

	private Set<JavaEditorFileListener> listeners;

	private ProjectBrowserServices browser;
	
	public static JavaEditorActivator getInstance() {
		return instance;
	}

	public JavaEditorActivator() {
		listeners = new HashSet<JavaEditorFileListener>();
	}


	public void addListener(JavaEditorFileListener l) {
		listeners.add(l);
	}

	public void removeListener(JavaEditorFileListener l) {
		listeners.remove(l);
	}

	public void notityOpenFile(File file) {
		for(JavaEditorFileListener l : listeners)
			l.fileOpened(file);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		browser = context.getService(ref);
		browser.addListener(listener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.context = null;
		browser.removeListener(listener);
	}

	public BundleContext getContext() {
		return context;
	}
	
	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		if(partRef.getId().equals(SimpleJavaEditor.EDITOR_ID)) {
			IEditorPart part = (IEditorPart) partRef.getPart(true);
			IEditorInput input = part.getEditorInput();
			String path = ((FileStoreEditorInput) input).getURI().getPath();
			File f = new File(path);
			for(JavaEditorFileListener l : listeners)
				l.fileOpened(f);
		}

	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub
		
	}




}
