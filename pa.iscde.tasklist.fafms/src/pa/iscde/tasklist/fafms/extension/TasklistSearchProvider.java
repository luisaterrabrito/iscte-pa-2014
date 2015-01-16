package pa.iscde.tasklist.fafms.extension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.tasklist.TaskView;
import pa.iscde.tasklist.TaskView.TodoLine;
import pa.iscde.tasklist.extensibility.Category;
import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.*;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TasklistSearchProvider implements SearchProvider {

	private ProjectBrowserServices browserServices;
	private JavaEditorServices editorServices;
	private PidescoServices pidescoServices;

	public TasklistSearchProvider() {

		Bundle bundle = FrameworkUtil.getBundle(TaskView.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<ProjectBrowserServices> ref = context
				.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref);

		ServiceReference<JavaEditorServices> serviceReference_javaEditor = context
				.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(serviceReference_javaEditor);

		ServiceReference<PidescoServices> serviceReference_pidesco = context
				.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
	}

	@Override
	public List<Object> getResults(String text) {

		ArrayList<Object> results = new ArrayList<Object>();
		PackageElement root = browserServices.getRootPackage();

		getTodo(root, results, text);

		return results;
	}

	//Get the values from task list table, the packages and classes and add it to the results list.
	private void getTodo(PackageElement root, List<Object> results, String text) {

		TaskView task = new TaskView();
		task.createCategories();
		task.populateData();

		for (SourceElement c : root) {
			
			if (c.getName().toUpperCase().contains(text.toUpperCase())) {
				results.add(c);
			}
			
			if (c.isPackage()) {
				getTodo((PackageElement) c, results, text);
			}

			for (TodoLine todo : task.getTodos()) {

				String className = todo.className;
				int line = todo.line;
				String comment = todo.text;
				Category category = todo.category;

				if(c.getName().equals(className + ".java")){
					results.add(category.getTag() + " " + comment + " in line " + line);
				}
			}
		}
	}

	//Defines the icon according to the object instance
	@Override
	public Image setImage(Object object) {
		if (object instanceof PackageElement)
			return pidescoServices.getImageFromPlugin(
					"pa.iscde.tasklist.fafms", "package_obj.gif");
		else if (object instanceof ClassElement)
			return pidescoServices.getImageFromPlugin(
					"pa.iscde.tasklist.fafms", "classes.gif");
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		if (object instanceof ClassElement) {
			SourceElement element = (SourceElement) object;
			File f = element.getFile();
			editorServices.openFile(f);
		}
	}

}
