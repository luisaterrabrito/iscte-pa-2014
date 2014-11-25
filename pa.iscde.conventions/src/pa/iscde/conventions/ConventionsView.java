package pa.iscde.conventions;


import java.io.File;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class ConventionsView implements PidescoView,ConventionService {


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
					verificaClass(javaServices.getOpenedFile(),"O nome da classe não deve começar com letra minuscula");
				}
			}
		});



		//verificar Metodos
		checkBoxMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxMethod.getSelection()){
					verificarLetraMetodo(javaServices.getOpenedFile(),"O metodo começa com letra maiuscula");
				}
			}
		});



		//Verificar o Tamanho dos mettodos
		checkBoxSize.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxSize.getSelection()){
					verificaTamanhoMetodo(javaServices.getOpenedFile(),"O nome do metodo é demasiado grande");
				}
			}
		});

		//Verificar Constantes
		checkBoxConstant.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxConstant.getSelection()){
					verificaConstantes(javaServices.getOpenedFile(),"O nome da constante não está de acordo com as convenções");
				}
			}
		});


		//Verificar Enumerados
		checkBoxEnum.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxEnum.getSelection()){
					verificaEnum(javaServices.getOpenedFile(),"Os enumerados têm que ter letra maiuscula");
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

	private void verificaClass(final File f, final String warning) {
		ASTVisitor v = new ASTVisitor() {
			@Override
			public boolean visit(TypeDeclaration node) {
				if(checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, warning, node.getName().getStartPosition(), node.getName().getLength());
				}
				return true;
			}
		};
		javaServices.parseFile(f, v);
	}

	private void verificarLetraMetodo(final File f,final String warning) {
		ASTVisitor v = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
				if(!node.isConstructor()){
					if(!checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){
						javaServices.addAnnotation(f, AnnotationType.WARNING, warning, node.getName().getStartPosition(), node.getName().getLength());
					}
				}
				return true;
			}

		};

		javaServices.parseFile(f, v);
	}

	private void verificaTamanhoMetodo(final File f, final String warning) {
		ASTVisitor v = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {

				if(!node.isConstructor()){
					if(verifySize(node.getName().getFullyQualifiedName())){
						javaServices.addAnnotation(f, AnnotationType.WARNING, warning, node.getName().getStartPosition(),  node.getName().getLength());
					}
				}
				return true;
			}

		};

		javaServices.parseFile(f, v);
	}

	private void verificaConstantes(final File f,final String warning) {
		ASTVisitor v = new ASTVisitor() {

			public boolean visit(VariableDeclarationFragment node) {
				if(checkVariableLowerCase(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, warning,
							node.getName().getStartPosition(), node.getName().getLength());
				}
				//como agregar warnings..

				if(checkVariableDollar(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, warning,
							node.getName().getStartPosition(), node.getName().getLength());
				}

				if(checkVariableUnderScore(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, warning,
							node.getName().getStartPosition(), node.getName().getLength());
				}
				return true;
			};

		};
		javaServices.parseFile(f, v);
	}

	private void verificaEnum(final File f,final String warning) {
		ASTVisitor v = new ASTVisitor() {

			public boolean visit(EnumConstantDeclaration node) {

				if(!checkVariableLowerCase(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, warning,
							node.getName().getStartPosition(), node.getName().getLength());
				}

				return true;
			}
		};

		javaServices.parseFile(f, v);
	}



	@Override
	public void verificarTudo(String warning, File f, TypeOf to) {
		switch (to) {
		case CLASS:
			verificaClass(f,warning);
			
			break;
		case METHOD:
			verificarLetraMetodo(f,warning);
			verificaTamanhoMetodo(f,warning);

			break;
		case CONSTANTS:
			verificaConstantes(f,warning);

			break;
		case ENUM:
			verificaEnum(f, warning);
			break;

		default:
			break;
		}
		
	}



}
