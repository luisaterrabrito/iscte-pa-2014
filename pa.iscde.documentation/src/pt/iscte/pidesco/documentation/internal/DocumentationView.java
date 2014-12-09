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
import pt.iscte.pidesco.documentation.service.ITagComment;

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
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;

		browser = new Browser(viewArea, SWT.NONE);
		browser.setText("<b>ola</b>");
		
		if (javaServices.getOpenedFile() != null){
			this.fillView();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillView() {
		this.cleanView();

		classDoc = new ClassDoc();

		ASTVisitor visitor = new ASTVisitor() {

			// TODO: Don't let doc except class and interfaces
			
			/**
			 * Método que apanha os comentários
			 */
			@Override
			public boolean visit(Javadoc node) {
				// Condition to get properties of the class
				if (node.getParent() instanceof TypeDeclaration) {
					classDoc.setClassFullName(((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());

					for (TagElement tag : (List<TagElement>) node.tags()) {
						// get tag class description
						for (Object fragment : tag.fragments()) {
							if (tag.getTagName() == null)
								classDoc.setComment(fragment.toString());
							else {
								// get all existing tags from class
								ITagComment tagDefinition = new TagCommentImpl(tag.getTagName(), fragment.toString());
								classDoc.getTags().add(tagDefinition);
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
								// get all existing tags from method
								ITagComment tagDefinition = new TagCommentImpl(tag.getTagName(), fragment.toString());
								methodDoc.getTags().add(tagDefinition);
							}
						}
					}
					
					// Add Method to Class
					classDoc.getListMethods().add(methodDoc);
				}
				
				//browser.setText("<b>" + classDoc.toString() + "</b>");
				return false;
			}

		};
		
		if(javaServices.getOpenedFile() != null){
			javaServices.parseFile(javaServices.getOpenedFile(), visitor);
		}
		
		// Apanha e escreve todas as tags
		// mas se apanhar uma tag de ponto de extensão, utiliza essa!!!

		// Coloca Texto no Browser
		
		
		//browser.setText("<b>Teste</b>");
		browser.setText(classDoc.toHTML().toString());
	}
	
	public void cleanView() {
		// TODO: clean browser view
		browser.redraw();

	}
	
	public static DocumentationView getInstance() {
		return instance;
	}

}
