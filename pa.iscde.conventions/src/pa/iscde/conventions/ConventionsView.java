package pa.iscde.conventions;

import java.awt.Color;
import java.awt.TextArea;
import java.awt.Window.Type;
import java.lang.invoke.MethodType;
import java.util.Map;



















































import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class ConventionsView implements PidescoView {


	private JavaEditorServices javaServices;



	public ConventionsView() {
		Bundle bundle = FrameworkUtil.getBundle(ConventionsView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);

	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		viewArea.setLayout(rowLayout);
		final Label label = new Label(viewArea, SWT.NONE);
		label.setText("Escolha uma das opções para Validar a Classe:");

		//Verificar classes.
		final Button checkBoxClass = new Button(viewArea, SWT.CHECK);
		checkBoxClass.setSize(10, 20);
		checkBoxClass.setText("Verificar a Primeira Letra da Classe");
		//Verificar Metodos
		final Button checkBoxMethod = new Button(viewArea, SWT.CHECK);
		checkBoxMethod.setSize(10, 20);
		checkBoxMethod.setText("Verificar a Primeira Letra dos Métodos");
		//Verificar o Tamanho dos mettodos
		final Button checkBoxSize = new Button(viewArea, SWT.CHECK);
		checkBoxSize.setSize(10, 20);
		checkBoxSize.setText("Verificar o tamanho dos Métodos");
		//Verificar Constantes
		final Button checkBoxConstant = new Button(viewArea, SWT.CHECK);
		checkBoxConstant.setSize(10, 20);
		checkBoxConstant.setText("Verificar Constantes/Variáveis (Minisculas, '$' , '_' )");
		//Verificar Enumerados
		final Button checkBoxEnum = new Button(viewArea, SWT.CHECK);
		checkBoxEnum.setSize(10, 20);
		checkBoxEnum.setText("Verificar Enumerados");


		//verificar classes.
		checkBoxClass.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){

				if(checkBoxClass.getSelection()){
					ASTVisitor v = new ASTVisitor() {
						@Override
						public boolean visit(TypeDeclaration node) {
							if(checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){
								javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "O nome da classe não deve começar com letra minuscula", node.getName().getStartPosition(), node.getName().getLength());
							}
							return true;
						}
					};
					javaServices.parseFile(javaServices.getOpenedFile(), v);
				}else{	
					//Apagar as anotações.
				}
			}



		});



		//verificar Metodos
		checkBoxMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxMethod.getSelection()){
					ASTVisitor v = new ASTVisitor() {
						@Override
						public boolean visit(MethodDeclaration node) {
							if(!node.isConstructor()){
								if(!checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){
									javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "O metodo começa com letra maiuscula", node.getName().getStartPosition(), node.getName().getLength());
								}
							}
							return true;
						}

					};

					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}else{
					//Remover a anotação
				}


			}
		});



		//Verificar o Tamanho dos mettodos
		checkBoxSize.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxSize.getSelection()){
					ASTVisitor v = new ASTVisitor() {
						@Override
						public boolean visit(MethodDeclaration node) {

							if(!node.isConstructor()){
								if(verifySize(node.getName().getFullyQualifiedName())){
									javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "O nome do metodo é demasiado grande", node.getName().getStartPosition(),  node.getName().getLength());
								}
							}
							return true;
						}

					};

					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}else{
					//Remover a anotação
				}


			}
		});

		//Verificar Constantes
		checkBoxConstant.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxConstant.getSelection()){


					ASTVisitor v = new ASTVisitor() {

						public boolean visit(VariableDeclarationFragment node) {
							if(checkVariableLowerCase(node.getName().getFullyQualifiedName())){
								javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "A variável tem que ter tudo minisculo",
										node.getName().getStartPosition(), node.getName().getLength());
							}
							//como agregar warnings..

							if(checkVariableDollar(node.getName().getFullyQualifiedName())){
								javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "A variável não deve começar com o caracter '$'",
										node.getName().getStartPosition(), node.getName().getLength());
							}

							if(checkVariableUnderScore(node.getName().getFullyQualifiedName())){
								javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "A variável não deve começar com o caracter '_'",
										node.getName().getStartPosition(), node.getName().getLength());
							}
							return true;
						};

					};
					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}else{
					//Remove anotação
				}
			}


		});


		//Verificar Enumerados
		checkBoxEnum.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxEnum.getSelection()){


					ASTVisitor v = new ASTVisitor() {

						public boolean visit(EnumConstantDeclaration node) {

							if(!checkVariableLowerCase(node.getName().getFullyQualifiedName())){
								javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "Os enumerados têm que ter letra maiuscula",
										node.getName().getStartPosition(), node.getName().getLength());
							}

							return true;
						}
					};

					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}else{
					//Apagar anotação

				}
			}

		});





		
		
	}



	public boolean checkFirstLetterLowerCase(String name){ 
		return Character.isLowerCase(name.charAt(0));
	}



	public boolean verifySize(String word){
		if(word.length()>=20){
			return true;
		}
		return false;
	}


	public boolean checkUnderScore(String name){
		return name.contains("_");
	}

	public boolean checkVariableUnderScore(String name){
		return (name.charAt(0)=='_');
	}

	public boolean checkVariableDollar(String name){
		return (name.charAt(0)=='$');
	}


	public boolean checkVariableLowerCase(String word){

		for(int i = 0; i!= word.length();i++){
			if(Character.isLowerCase(word.charAt(i))){
				return false;
			}
		}
		return true;
	}


}
