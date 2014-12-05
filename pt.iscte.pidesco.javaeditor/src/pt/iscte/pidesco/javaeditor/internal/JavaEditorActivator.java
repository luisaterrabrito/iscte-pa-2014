package pt.iscte.pidesco.javaeditor.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class JavaEditorActivator implements BundleActivator, IPartListener2 {

	private static JavaEditorActivator instance;

	private BundleContext context;
	
	private ProjectBrowserListener listener;

	private Set<JavaEditorListener> listeners;

	private ProjectBrowserServices browser;
	
	private JavaEditorServices services;
	

	private ISelectionListener selectionListener;
	
	
	public static JavaEditorActivator getInstance() {
		return instance;
	}
	
	public JavaEditorServices getServices() {
		return services;
	}

	public JavaEditorActivator() {
		listeners = new HashSet<JavaEditorListener>();
	}

	public void addListener(JavaEditorListener l) {
		listeners.add(l);
	}

	public void removeListener(JavaEditorListener l) {
		listeners.remove(l);
	}

	public void notityOpenFile(File file) {
		for(JavaEditorListener l : listeners)
			l.fileOpened(file);
	}

	public void notityClosedFile(File file) {
		for(JavaEditorListener l : listeners)
			l.fileClosed(file);
	}
	
	public void notitySavedFile(File file) {
		for(JavaEditorListener l : listeners)
			l.fileSaved(file);
	}
	
	public void notifySelectionChanged(File file, String text, int offset, int length) {
		for(JavaEditorListener l : listeners)
			l.selectionChanged(file, text, offset, length);
	}
	
	@Override
	public void start(final BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		final ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		browser = context.getService(ref);
		services = new JavaEditorServicesImpl();
		listener = new OpenEditorListener(services);
		browser.addListener(listener);
		context.registerService(JavaEditorServices.class, services, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.context = null;
	}

	public BundleContext getContext() {
		return context;
	}
	
	private File getFile(IWorkbenchPartReference partRef) {
		IEditorPart part = (IEditorPart) partRef.getPart(true);
		IEditorInput input = part.getEditorInput();
		String path = ((FileStoreEditorInput) input).getURI().getPath();
		File f = new File(path);
		return f;
	}
	
	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		if(selectionListener == null) {
			selectionListener = new ISelectionListener() {

				@Override
				public void selectionChanged(IWorkbenchPart part, ISelection selection) {
					if(part instanceof IEditorPart && ((IEditorPart) part).getEditorSite().getId().equals(SimpleJavaEditor.EDITOR_ID)) {
						IEditorPart editor = (IEditorPart) part; 
						IEditorInput input = editor.getEditorInput();
						String path = ((FileStoreEditorInput) input).getURI().getPath();
						File f = new File(path);
						TextSelection s = (TextSelection) selection;
						notifySelectionChanged(f, s.getText(), s.getOffset(), s.getLength());
					}
				}
			};
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addSelectionListener(selectionListener);
		}
	}
	
	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		if(partRef.getId().equals(SimpleJavaEditor.EDITOR_ID))
			notityOpenFile(getFile(partRef));
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		if(partRef.getId().equals(SimpleJavaEditor.EDITOR_ID))
			notityClosedFile(getFile(partRef));
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
