package pt.iscte.pidesco.documentation.internal;

import java.awt.Color;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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

			public boolean visit(org.eclipse.jdt.core.dom.LineComment node) {
				if (node.isLineComment())
					l2.setText("Pimbas");
				return false;
			};
			
			
			@Override
			public boolean visit(Javadoc node) {
				return false;
			}
			
			@Override
			public boolean visit(BlockComment node) {
				if (node.isBlockComment())
					l2.setText("Encontrei");
				else
					l2.setText("N�o Encontrei");
				
				return false;
			};
			
			@Override
			public boolean visit(MethodDeclaration node) {
				//l.setText(node.getName().toString());
				TableItem oneTableItem = new TableItem(table, SWT.NONE);
				oneTableItem.setText(0, node.getName().toString());
				oneTableItem.setData(node.getName().toString());
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
