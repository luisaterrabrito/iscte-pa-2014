package pa.iscde.filtersearch.providers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.view.SearchCategory;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ProjectBrowserSearchProvider implements SearchProvider {

	private ProjectBrowserServices browserServices;
	private JavaEditorServices editorServices;
	private PidescoServices pidescoServices;
	

	public ProjectBrowserSearchProvider() {

		Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref);

		ServiceReference<JavaEditorServices> serviceReference_javaEditor = context.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(serviceReference_javaEditor);
		
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
	}

	@Override
	public List<Object> getResults(String text) {
		List<Object> hits = new ArrayList<>();
		PackageElement root = browserServices.getRootPackage();
		
		scan(root,hits,text);
		return hits;
	}

	/**
	 * Efectua a verificação de todos os elementos incluídos no nó raiz (root). Consoante o seu tipo, será executada uma acção diferente.
	 * No presente caso, o do ProjectBrowser, se o elemento for um package, utiliza-se recursividade do método scan; caso não seja, isto é,
	 * se for uma classe, interface ou outro ficheiro ".java", e o seu nome contiver o texto inserido no campo de procura,
	 * é adicionado à lista dos resultados alvo (hits).
	 * 
	 * @param root
	 * @param hits
	 * @param text
	 */
	private void scan(PackageElement root, List<Object> hits, String text) {
		for(SourceElement c : root){
			if(c.getName().toUpperCase().contains(text.toUpperCase()))
				hits.add(c);
			if(c.isPackage()){
				scan((PackageElement)c,hits, text);				
			}
		}
	}


	/**
	 * Define o icon a apresentar consoante o tipo de object passado como argumento.
	 * 
	 *  @param object
	 */
	@Override
	public Image setImage(Object object) {
		if(object instanceof SearchCategory)
			return pidescoServices.getImageFromPlugin("pa.iscde.filtersearch", "searchtool.gif");
		else if(object instanceof PackageElement)
			return pidescoServices.getImageFromPlugin("pa.iscde.filtersearch", "package_obj.gif");
		else if(object instanceof ClassElement)
			return pidescoServices.getImageFromPlugin("pa.iscde.filtersearch", "classes.gif");
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		if (object instanceof ClassElement){
			SourceElement element = (SourceElement) object;
			File f = element.getFile();
			editorServices.openFile(f);
		}
	}
}