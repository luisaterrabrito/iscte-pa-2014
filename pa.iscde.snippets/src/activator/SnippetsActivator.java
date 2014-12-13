package activator;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.snippets.extensionhandlers.ProgrammaticSnippets;
import pa.iscde.snippets.extensionhandlers.SnippetContextDefinitionManager;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class SnippetsActivator implements BundleActivator {
	private static SnippetsActivator instance;
	private static final String SNIPPETS_VIEW_ID = "pa.iscde.snippets.snippets";
	private BundleContext context;
	private EditorListenerAdapter editorListener = new EditorListenerAdapter();
	private JavaEditorServices editorServices;
	private PidescoServices pidescoServices;

	public static SnippetsActivator getInstance() {
		return instance;
	}

	public String getSelectedText() {
		return editorListener.getSelectedText();
	}
	
	public void insertTextAt(String text){
		editorServices.insertTextAtCursor(text);
	}
	
	public File getOpenFile(){
		return editorServices.getOpenedFile();
	}
	
	public int getCursorPosition(){
		return editorServices.getCursorPosition();
	}
	
	public void openSnippetsView(){
		pidescoServices.openView(SNIPPETS_VIEW_ID);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		ServiceReference<JavaEditorServices> ref1 = this.context
				.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(ref1);
		ServiceReference<PidescoServices> ref2 = this.context.getServiceReference(PidescoServices.class);
		pidescoServices = this.context.getService(ref2);
		editorServices.addListener(editorListener);
		(new ProgrammaticSnippets()).createNewSnippetsProgrammatically();
		SnippetContextDefinitionManager.getInstance().loadDefinitions();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.context = null;
		editorServices.removeListener(editorListener);
	}
}
