package pa.iscde.formulas.tmsas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.formulas.draw.EquationFinder;
import pa.iscde.formulas.util.FileReadUtil;
import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.extensibility.PidescoServices;


public class MathSearch implements SearchProvider {
	
	private PidescoServices pidescoServices;
	public static final String PLUGIN_ID = "pa.iscde.formulas.tmsas";
	
	public MathSearch() {
		Bundle bundle = FrameworkUtil.getBundle(SearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
	}

	@Override
	public List<Object> getResults(String text) {
		List<Object> t = new LinkedList<Object>();
		File file = FileReadUtil.loadFile(PLUGIN_ID,"formulas");
		if(file.isDirectory()){
			File[] listOfFiles = file.listFiles();
			for (File file2 : listOfFiles) {
				t.add(file2);
			}
		}
		return t;
	}

	@Override
	public Image setImage(Object object) {
		if(object instanceof File){
			return pidescoServices.getImageFromPlugin(PLUGIN_ID, "draw.gif");
		}
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		try {
			EquationFinder eq = new EquationFinder((File) object);
			if(FormulasView.getInstance()!=null){
				if(!FormulasView.getInstance().getWindowState())
					FormulasView.getInstance().setDrawEquationMode(eq);
				else
					System.out.println("Ins");
			}
			else
				System.out.println("In");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
