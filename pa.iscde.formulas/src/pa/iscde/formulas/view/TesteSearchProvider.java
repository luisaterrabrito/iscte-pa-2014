package pa.iscde.formulas.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;





import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.ProjectBrowserSearchProvider;
import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TesteSearchProvider implements SearchProvider{

	private PidescoServices pidescoServices;
	private ProjectBrowserServices browserServices;

	public TesteSearchProvider() {
		Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);

		ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref);
		
		
	}

	@Override
	public List<Object> getResults(String text) {

		List<Object> hits = new ArrayList<>();
		PackageElement root = new PackageElement(null, "TESTE", new File("pa.iscde.formulas.Formula"));

	

		scan(root,hits,text);
		return hits;

	}

	private void scan(PackageElement root, List<Object> hits, String text) {
		for(SourceElement c : root){
			if(c.getName().toUpperCase().contains(text.toUpperCase()))
				hits.add(c);
			if(c.isPackage()){
				scan((PackageElement)c,hits, text);				
			}
		}
	}

	@Override
	public Image setImage(Object object) {

		if(object instanceof SearchProvider)
			return pidescoServices.getImageFromPlugin("pa.iscde.formulas", "draw.gif");
		else if(object instanceof PackageElement)
			return pidescoServices.getImageFromPlugin("pa.iscde.formulas", "gonçalo.gif");
		else if(object instanceof ClassElement)
			return pidescoServices.getImageFromPlugin("pa.iscde.formulas", "tiago.gif");
		return null;			
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		// TODO Auto-generated method stub

	}

}
