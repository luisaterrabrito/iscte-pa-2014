package pt.iscte.pidesco.documentation.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

import pt.iscte.pidesco.documentation.internal.TagCommentImpl;

public class DocumentationView implements PidescoView {

	private static DocumentationView instance;

	private JavaEditorServices javaServices;
	//private ProjectBrowserServices browserServices;

	private Browser browser;
	private ClassDoc classDoc;
	
	public DocumentationView() {
		Bundle bundle = FrameworkUtil.getBundle(DocumentationView.class);
		BundleContext context  = bundle.getBundleContext();
		
		ServiceReference<JavaEditorServices> refEditor = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(refEditor);
		
		//ServiceReference<ProjectBrowserServices> refProjectBrowser = context.getServiceReference(ProjectBrowserServices.class);
		//browserServices = context.getService(refProjectBrowser);
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		
		instance = this;

		browser = new Browser(viewArea, SWT.NONE);
		browser.setText("<b>ola</b>");
		
		classDoc = new ClassDoc();
		
		//table = new Table(viewArea, SWT.NONE);
		//TableColumn nameCol = new org.eclipse.swt.widgets.TableColumn(table, SWT.NONE);
		//nameCol.setText("Name");
		//nameCol.setWidth(200);
		//TableColumn descriptionCol = new org.eclipse.swt.widgets.TableColumn(table, SWT.NONE);
		//descriptionCol.setText("Description");
		//descriptionCol.setWidth(200);
		
		//table.setHeaderVisible(true);

		if (javaServices.getOpenedFile() != null){
			this.fillView();
		}
		
	}

	@SuppressWarnings("unchecked")
	public void fillView() {
		this.cleanView();
		
		ASTVisitor visitor = new ASTVisitor() {

			// TODO: Don't let doc except class and interfaces
			
			/**
			 * Método que apanha os comentários
			 */
			@Override
			public boolean visit(Javadoc node) {
				//TableItem item = new TableItem(table, SWT.NONE);

				// Condition to get properties of the class
				if (node.getParent() instanceof TypeDeclaration) {
					classDoc.setClassFullName(((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());

					for (TagElement tag : (List<TagElement>) node.tags()) {
						// get tag class description
						for (Object fragment : tag.fragments()) {
							if (tag.getTagName() == null)
								classDoc.setComment(fragment.toString());
							else {
								// Set tags from class TODO

							}

						}
					}
				}
				
				// Condition to get properties of the construtor TODO

				
				// Condition to get properties of the method
				if (node.getParent() instanceof MethodDeclaration) {
					MethodDoc methodDoc = new MethodDoc();
					methodDoc.setMethodName(((MethodDeclaration) node.getParent()).getName().getFullyQualifiedName());

					// get signiture TODO
					
					for (TagElement tag : (List<TagElement>) node.tags()) {
						// get tag method description
						for (Object fragment : tag.fragments()) {
							if (tag.getTagName() == null)
								methodDoc.setComment(fragment.toString());
							else {
								// Set tags from method TODO
								
							}

						}
					}
					
					// Add Method to Class
					classDoc.getListMethods().add(methodDoc);
				}
				
				browser.setText("<b>" + classDoc.toString() + "</b>");
				return false;
			}

		};
		
		if(javaServices.getOpenedFile() != null){
			javaServices.parseFile(javaServices.getOpenedFile(), visitor);
		}
		
	}
	
	public void cleanView() {
		// TODO: clean browser view
		browser.redraw();

	}
	
	public static DocumentationView getInstance() {
		return instance;
	}

}
