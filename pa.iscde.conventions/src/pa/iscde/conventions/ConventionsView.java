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
					verificaClass(javaServices.getOpenedFile());
				}
			}
		});



		//verificar Metodos
		checkBoxMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxMethod.getSelection()){
					verificarLetraMetodo(javaServices.getOpenedFile());
				}
			}
		});



		//Verificar o Tamanho dos mettodos
		checkBoxSize.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxSize.getSelection()){
					verificaTamanhoMetodo(javaServices.getOpenedFile());
				}
			}
		});

		//Verificar Constantes
		checkBoxConstant.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxConstant.getSelection()){
					verificaConstantes(javaServices.getOpenedFile());
				}
			}
		});


		//Verificar Enumerados
		checkBoxEnum.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxEnum.getSelection()){
					verificaEnum(javaServices.getOpenedFile());
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

	private void verificaClass(final File f) {
		ASTVisitor v = new ASTVisitor() {
			@Override
			public boolean visit(TypeDeclaration node) {
				if(checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, "O nome da classe não deve começar com letra minuscula", node.getName().getStartPosition(), node.getName().getLength());
				}
				return true;
			}
		};
		javaServices.parseFile(f, v);
	}

	private void verificarLetraMetodo(final File f) {
		ASTVisitor v = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
				if(!node.isConstructor()){
					if(!checkFirstLetterLowerCase(node.getName().getFullyQualifiedName())){
						javaServices.addAnnotation(f, AnnotationType.WARNING, "O metodo começa com letra maiuscula", node.getName().getStartPosition(), node.getName().getLength());
					}
				}
				return true;
			}

		};

		javaServices.parseFile(f, v);
	}

	private void verificaTamanhoMetodo(final File f) {
		ASTVisitor v = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {

				if(!node.isConstructor()){
					if(verifySize(node.getName().getFullyQualifiedName())){
						javaServices.addAnnotation(f, AnnotationType.WARNING, "O nome do metodo é demasiado grande", node.getName().getStartPosition(),  node.getName().getLength());
					}
				}
				return true;
			}

		};

		javaServices.parseFile(f, v);
	}

	private void verificaConstantes(final File f) {
		ASTVisitor v = new ASTVisitor() {

			public boolean visit(VariableDeclarationFragment node) {
				if(checkVariableLowerCase(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, "A variável tem que ter tudo minisculo",
							node.getName().getStartPosition(), node.getName().getLength());
				}
				//como agregar warnings..

				if(checkVariableDollar(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, "A variável não deve começar com o caracter '$'",
							node.getName().getStartPosition(), node.getName().getLength());
				}

				if(checkVariableUnderScore(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, "A variável não deve começar com o caracter '_'",
							node.getName().getStartPosition(), node.getName().getLength());
				}
				return true;
			};

		};
		javaServices.parseFile(f, v);
	}

	private void verificaEnum(final File f) {
		ASTVisitor v = new ASTVisitor() {

			public boolean visit(EnumConstantDeclaration node) {

				if(!checkVariableLowerCase(node.getName().getFullyQualifiedName())){
					javaServices.addAnnotation(f, AnnotationType.WARNING, "Os enumerados têm que ter letra maiuscula",
							node.getName().getStartPosition(), node.getName().getLength());
				}

				return true;
			}
		};

		javaServices.parseFile(f, v);
	}


}
