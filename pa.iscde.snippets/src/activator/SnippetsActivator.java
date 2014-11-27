package activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class SnippetsActivator implements BundleActivator {
	private static SnippetsActivator instance;
	private BundleContext context;
	private EditorListenerAdapter editorListener = new EditorListenerAdapter();
	private JavaEditorServices editorServices;

	public static SnippetsActivator getInstance() {
		return instance;
	}

	public String getSelectedText() {
		return editorListener.getSelectedText();
	}
	
	public void insertTextAt(String text){
		editorServices.insertTextAtCursor(text);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		ServiceReference<JavaEditorServices> ref = context
				.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(ref);
		editorServices.addListener(editorListener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.context = null;
		editorServices.removeListener(editorListener);
	}

}
