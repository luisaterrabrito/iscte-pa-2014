package pa.iscde.formulas.tmsas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.formulas.util.EquationFinder;
import pa.iscde.formulas.view.FormulasView;


public class MathSearch implements SearchProvider {


	@Override
	public List<Object> getResults(String text) {
		List<Object> t = new LinkedList<Object>();
		Bundle bundle = Platform.getBundle("pa.iscde.formulas.tmsas");
		URL fileURL = bundle.getEntry("formulas");
		File file = null;
		try {
		    file = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
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
		if(object instanceof File && FormulasView.getInstance()!=null)
			return FormulasView.getInstance().getImages().get("draw.gif");
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		try {
			EquationFinder eq = new EquationFinder((File) object);
			if(FormulasView.getInstance()!=null)
				FormulasView.getInstance().setDrawEquationMode(eq);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
