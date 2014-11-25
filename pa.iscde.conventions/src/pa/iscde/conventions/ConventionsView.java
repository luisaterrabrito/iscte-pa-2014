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
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
		Shell shell = new Shell(); //duvida na shell
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		viewArea.setLayout(rowLayout);



//		ASTVisitor v = new ASTVisitor() {
//
//
//			@Override
//			public boolean visit(TypeDeclaration node) {
//			System.out.println(node.getName());
//				return super.visit(node);
//			}
//	
//
//
//
//
//
//
//		};

//		javaServices.parseFile(javaServices.getOpenedFile(), v);

		final Label label = new Label(viewArea, SWT.NONE);
		label.setText("Escolha uma das opções para Validar a Classe:");

		//Verificar classes.
		final Button botao = new Button(viewArea, SWT.CHECK);
		botao.setSize(10, 20);
		botao.setText("Check First Letter of a Class");
		botao.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){

				if(botao.getSelection()){




					ASTVisitor v = new ASTVisitor() {




						@Override
						public boolean visit(TypeDeclaration node) {

							int sizeMethod = 0;
							int offset = 0;

						

								if(checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){

									sizeMethod = node.getName().getLength();
									//offset é o inicio do primeiro caracter.
									//Length é o tamanho da seleção.
									offset =node.getName().getStartPosition();	


									javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.ERROR, "O nome da classe não pode começar com letra minuscula", offset, sizeMethod);
								}




							



							return true;
						}

					};

					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}	

				//					if(checkFirstLetterLowerCase(javaServices.getOpenedFile().getName())){
				//						
				//						//marcar o nome da classe.
				//						System.out.println("A classe " +javaServices.getOpenedFile().getName() + " começa com letra Minuscula");
				//					}else{
				//						//apagar quando ja tiver a fazer highlight
				//						System.out.println("A classe " +javaServices.getOpenedFile().getName() + " começa com letra Maiuscula");
				//					};
				//				}
			}



		});



		//Verificar Metodos
		final Button botaoMaior = new Button(viewArea, SWT.CHECK);
		botaoMaior.setSize(10, 20);
		botaoMaior.setText("Check First Letter of a Method");
		botaoMaior.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){
				if(botaoMaior.getSelection()){





					ASTVisitor v = new ASTVisitor() {




						@Override
						public boolean visit(MethodDeclaration node) {

							int sizeMethod = 0;
							int offset = 0;

							if(!node.isConstructor()){
								if(!checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){

									sizeMethod = node.getName().getLength();
									//offset é o inicio do primeiro caracter.
									//Length é o tamanho da seleção.
									offset =node.getName().getStartPosition();	


									javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "O metodo começa com letra maiuscula", offset, sizeMethod);
								}




							}



							return true;
						}

					};

					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}else{

				}


			}
		});






		final Button botaoverifySize = new Button(viewArea, SWT.CHECK);
		botaoverifySize.setSize(10, 20);
		botaoverifySize.setText("Verify Size");
		botaoverifySize.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){
				if(botaoverifySize.getSelection()){





					ASTVisitor v = new ASTVisitor() {




						@Override
						public boolean visit(MethodDeclaration node) {

							int sizeMethod = 0;
							int offset = 0;

							if(!node.isConstructor()){

								if(verifySize(node.getName().getFullyQualifiedName())){

									sizeMethod = node.getName().getLength();
									//offset é o inicio do primeiro caracter.
									//Length é o tamanho da seleção.
									offset =node.getName().getStartPosition();	

									javaServices.addAnnotation(javaServices.getOpenedFile(), AnnotationType.WARNING, "O nome do metodo é demasiado grande", offset, sizeMethod);

								}

							}



							return true;
						}

					};

					javaServices.parseFile(javaServices.getOpenedFile(), v);

				}else{

				}


			}
		});






	}

	public boolean checkFirstLetterLowerCase(String name){ //temporarimente boolean

		if(Character.isLowerCase(name.charAt(0))){
			return true;
		}

		return false;

	}




	public boolean verifySize(String word){

		if(word.length()>=20){
			return true;
		}

		return false;

	}


}
