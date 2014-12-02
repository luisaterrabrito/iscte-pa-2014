package pt.iscte.pidesco.documentation.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class DocumentationView implements PidescoView {

	private static DocumentationView instance;
	
	private JavaEditorServices javaServices;
	private ProjectBrowserServices browserServices;
	
	private Label l;
	private Label l2;
	private Table table;

	private ClassDoc classDoc;
	
	public DocumentationView() {
		Bundle bundle = FrameworkUtil.getBundle(DocumentationView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> refEditor = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(refEditor);
		
		ServiceReference<ProjectBrowserServices> refProjectBrowser = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(refProjectBrowser);
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		
		classDoc = new ClassDoc();
		
//		l = new Label(viewArea, SWT.NONE);
//		l.setText("Inicio");
//
//		l2 = new Label(viewArea, SWT.DOWN);
//		l2.setText("Inicio 2");
		
		table = new Table(viewArea, SWT.NONE);
		TableColumn nameCol = new org.eclipse.swt.widgets.TableColumn(table, SWT.NONE);
		nameCol.setText("Name");
		nameCol.setWidth(200);
		TableColumn descriptionCol = new org.eclipse.swt.widgets.TableColumn(table, SWT.NONE);
		descriptionCol.setText("Description");
		descriptionCol.setWidth(200);
		
		table.setHeaderVisible(true);

		if (javaServices.getOpenedFile() != null){
			this.draw();
		}
		
	}

	public void draw() {
		this.cleanView();		
		ASTVisitor visitor = new ASTVisitor() {

			/**
			 * Método que apanha os comentários
			 */
			@Override
			public boolean visit(Javadoc node) {
				TableItem item = new TableItem(table, SWT.NONE);

				// Condition to get properties of the class
				if (node.getParent() instanceof TypeDeclaration) {
					classDoc.setClassFullName(((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());
					item.setText(1, "Classe Nome: " + ((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());

				}
				
				// Condition to get properties of the method
				if (node.getParent() instanceof MethodDeclaration) {
					item.setText(1, "Método Nome: " + ((MethodDeclaration) node.getParent()).getName());
					//item.setText(1, "Método Nome: " + ((MethodDeclaration) node.getParent()).toString());
				}
				
				String s = "";
				int i = 0;

				// Percorrer a lista de tags
				// Primeira tag -> getTagName se null é o descritivo senão é uma tag
				for (TagElement tag : (List<TagElement>) node.tags()) {
						s += tag.getTagName() + " > ";

						// Apanhar os descritivos associados às tags
						for (Object fragment : tag.fragments()) {
							s += fragment.toString() + " ||| ";
					}
					
					item.setText(0, s);
					item.setData(tag);
					i++;
					s = "";
				};
				
				return false;
			}

		};
		
		if(javaServices.getOpenedFile() != null){
			javaServices.parseFile(javaServices.getOpenedFile(), visitor);
		}
		
	}
	
	public void cleanView() {
		//l.setText("Nenhum ficheiro aberto!");
		//if(table.getItems().length != 0){
			for(TableItem item : table.getItems()) {
				item.dispose();
			}
		//}
		
		
	}
	
	public static DocumentationView getInstance() {
		return instance;
	}

}
