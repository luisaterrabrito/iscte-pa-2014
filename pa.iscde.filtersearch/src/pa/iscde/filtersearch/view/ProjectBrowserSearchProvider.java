package pa.iscde.filtersearch.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.filtersearch.view.SearchView.ViewLabelProvider;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ProjectBrowserSearchProvider implements SearchProvider {

		private ProjectBrowserServices browserServices;
		private JavaEditorServices editorServices;
		private ViewLabelProvider labelProvider;

		public ProjectBrowserSearchProvider() {

			Bundle bundle = FrameworkUtil.getBundle(ProjectBrowserSearchProvider.class);
			BundleContext context  = bundle.getBundleContext();
			ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
			browserServices = context.getService(ref);

			ServiceReference<JavaEditorServices> serviceReference_javaEditor = context.getServiceReference(JavaEditorServices.class);
			editorServices = context.getService(serviceReference_javaEditor);
		}

		@Override
		public List<Object> getResults(String text) {
			List<Object> hits = new ArrayList<>();
			PackageElement root = browserServices.getRootPackage();



			scan(root,hits,text);

			return hits;
		}

		private void scan(PackageElement root, List<Object> hits, String text) {



			Map<ClassElement, String> resultsMethods = new HashMap<ClassElement, String>();

			for(SourceElement c : root){

				if(c.getName().toUpperCase().contains(text.toUpperCase()))
					hits.add(c);

				if(c.isClass()){
					verifyExistingMethodsInsideClass((ClassElement)c, text, resultsMethods);
				} else {
					scan((PackageElement)c,hits, text);				
				}
			}
		}

		/** 
		 *
		 * TODO
		 * 
		 * acabar a verifica��o da existencia de m�todos
		 *
		 */
		private void verifyExistingMethodsInsideClass(ClassElement c, String text, Map<ClassElement, String> resultsMethods) {



			Scanner fileScanner = null;
			boolean fileEnd = false;

			File f  = c.getFile();
			
			System.out.println("File name: " + c.getFile().getName());
			try { 
				fileScanner = new Scanner(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			while(!fileEnd){

				if(fileScanner.hasNextLine()){

					Scanner lineScanner = new Scanner(fileScanner.nextLine());

					if(lineScanner.hasNextLine()){

						String line = lineScanner.nextLine();
						
						System.out.println(line);

						if(line.contains(text) && !text.isEmpty()){
							resultsMethods.put(c, line);
							System.out.println("Tenho um resultado");
						}

					} else {
						fileEnd = true;
						printMap(resultsMethods);
					}
				}
			}
		}

		private void printMap(Map<ClassElement, String> resultsMethods) {
			System.out.println("--------------- RESULTS ---------------");
			for (Map.Entry<ClassElement, String> entry : resultsMethods.entrySet()) {
				System.out.println(entry.getKey() + " - " + entry.getValue());
			}
		}

		@Override
		public Image setImage(Object object) {
			return labelProvider.getImage(object);
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