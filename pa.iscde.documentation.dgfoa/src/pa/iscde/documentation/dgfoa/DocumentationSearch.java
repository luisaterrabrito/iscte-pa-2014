package pa.iscde.documentation.dgfoa;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * This class/extension is to search for documentation tags and their descriptions on files
 * 
 * @author David Franco
 */
public class DocumentationSearch implements SearchProvider {

	private PidescoServices pidescoServices;
	private ProjectBrowserServices browserServices;
	private JavaEditorServices editorServices;
	
	/**
	 * Construtor
	 */
	public DocumentationSearch() {

		Bundle bundle = FrameworkUtil.getBundle(SearchProvider.class);
		BundleContext context  = bundle.getBundleContext();

		ServiceReference<PidescoServices> refPidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(refPidesco);

		ServiceReference<ProjectBrowserServices> refBrowser = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(refBrowser);

		ServiceReference<JavaEditorServices> refEditor = context.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(refEditor);
		
	}

	/**
	 * Method to search documentation tags and their descriptions giving some text
	 *  
	 * @return list of objects with the results founded
	 */
	@Override
	public List<Object> getResults(String textToSearch) {

		List<Object> filesFound = new ArrayList<Object>();

		PackageElement packageRoot = browserServices.getRootPackage();

		try {
			scanForFiles(packageRoot, textToSearch, filesFound);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return filesFound;
	}

	/**
	 * Reflection Method to search the documentation tags and their descriptions on each file
	 * 
	 * @param packageRoot - element
	 * @param textToSearch - text to search
	 * @param filesFound - list of objects founded 
	 */
	private void scanForFiles(PackageElement packageRoot, String textToSearch, List<Object> filesFound) throws IOException {
		
		for (SourceElement source : packageRoot) {
			if (source.isPackage()) {
				scanForFiles((PackageElement) source, textToSearch, filesFound);				
			} else {
				
				for (String line : Files.readAllLines(Paths.get(source.getFile().getAbsolutePath()), StandardCharsets.ISO_8859_1)) {
					if (line.toLowerCase().contains("@" + textToSearch.toLowerCase())) {
						filesFound.add(source);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Method to assign a image for each element founded on the search
	 */
	@Override
	public Image setImage(Object object) {
		return pidescoServices.getImageFromPlugin("pa.iscde.documentation.dgfoa", "javadoc.gif");
	}

	/**
	 * Method for open the selected file in JavaEditor View and Documentation View
	 */
	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		
		if (object instanceof ClassElement) {
			editorServices.openFile(((ClassElement) object).getFile());
		}

	}

}
