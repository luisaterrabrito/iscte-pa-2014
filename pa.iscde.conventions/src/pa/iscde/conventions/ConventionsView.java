package pa.iscde.conventions;


import java.io.File;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.conventions.extensability.Cobject;
import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.FilterByModifier;
import pa.iscde.conventions.extensability.TypeOf;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class ConventionsView implements PidescoView {


	private JavaEditorServices javaServices;

	private static final String EXT_POINT_CONVENTION = "pa.iscde.conventions.conventionservice";
	private static final String EXT_POINT_MODIFIER = "pa.iscde.conventions.filterbymodifier";

	private LinkedList<ConventionService> lista;
	private LinkedList<FilterByModifier> listModifier;

	public ConventionsView() {
		Bundle bundle = FrameworkUtil.getBundle(ConventionsView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
	}


	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {


		ConventionService cs = new ConventionService() {

			@Override
			public Cobject verificarConvencao(String name, TypeOf to) {
				switch (to) {
				case CLASS:{

					if(checkFirstLetterLowerCase(name)){
						return new Cobject("O nome da classe não deve começar com letra minuscula", checkFirstLetterLowerCase(name));
					}else{
						return new Cobject("", false);
					}
				}


				case METHOD:{

					if(!checkFirstLetterLowerCase(name))
						return new Cobject("O metodo começa com letra maiuscula",!checkFirstLetterLowerCase(name));

					if(verifySize(name)){
						return new Cobject("O nome do metodo é demasiado grande",verifySize(name));
					}else{
						return new Cobject("", false);
					}
				}



				case CONSTANTS:{
					if(checkVariableLowerCase(name)){
						return new Cobject("O nome da constante não está de acordo com as convenções(variavel com letra maiuscula)",checkVariableLowerCase(name));
					}
					if(checkVariableDollar(name)){
						return new Cobject("O nome da constante não está de acordo com as convenções(variavel com dollar)",checkVariableDollar(name));
					}

					if(checkVariableUnderScore(name)){
						return new Cobject("O nome da constante não está de acordo com as convenções(variavel com underscore no inicio)",checkVariableUnderScore(name));
					}else{
						return new Cobject("", false);
					}
				}



				case ENUM:{
					if(!checkVariableLowerCase(name)){
						return new Cobject ("Os enumerados têm que ter letra maiuscula", !checkVariableLowerCase(name));
					}else{
						return new Cobject("", false);
					}
				}

				default:
					return new Cobject("",false);
				}
			}
		};


		lista = new LinkedList<ConventionService>();
		lista.add(cs);

		IExtensionRegistry reg = Platform.getExtensionRegistry();

		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(EXT_POINT_CONVENTION);

		for(IConfigurationElement ext : extensions){


			try {

				ConventionService s = (ConventionService) ext.createExecutableExtension("class");
				lista.add(s);


			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}




		listModifier = new LinkedList<FilterByModifier>();


		IExtensionRegistry regMod = Platform.getExtensionRegistry();

		IConfigurationElement[] extensionsMod = regMod.getConfigurationElementsFor(EXT_POINT_MODIFIER);

		for(IConfigurationElement ext : extensionsMod){


			try {

				FilterByModifier m = (FilterByModifier) ext.createExecutableExtension("filterbymodifier");
				listModifier.add(m);

			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}



		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		viewArea.setLayout(rowLayout);
		final Label label = new Label(viewArea, SWT.NONE);
		label.setText("Escolha uma das opções para Validar a Classe:");

		//Verificar classes.
		final Button checkBoxClass = new Button(viewArea, SWT.CHECK);
		checkBoxClass.setSize(10, 20);
		checkBoxClass.setText("Verificar Classe");
		//Verificar Metodos
		final Button checkBoxMethod = new Button(viewArea, SWT.CHECK);
		checkBoxMethod.setSize(10, 20);
		checkBoxMethod.setText("Verificar Todos os Métodos ");
		//Verificar Constantes
		final Button checkBoxConstant = new Button(viewArea, SWT.CHECK);
		checkBoxConstant.setSize(10, 20);
		checkBoxConstant.setText("Verificar Constantes/Variáveis (Minisculas, '$' , '_' )");
		//Verificar Enumerados
		final Button checkBoxEnum = new Button(viewArea, SWT.CHECK);
		checkBoxEnum.setSize(10, 20);
		checkBoxEnum.setText("Verificar Enumerados");

		final Button checkBoxMethodMod = new Button(viewArea, SWT.CHECK);
		checkBoxMethodMod.setSize(10, 20);
		
		if(listModifier.isEmpty()){
		checkBoxMethodMod.setText("Filtrar Método por Modifier igual a : ");
		}else{
			checkBoxMethodMod.setText("Filtrar Método por Modifier");
		}

		final Combo comboDropDown = new Combo(viewArea, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		comboDropDown.setVisible(false);
		
		if(listModifier.isEmpty()){
		comboDropDown.setVisible(true);
		comboDropDown.add("PUBLIC");
		comboDropDown.add("PRIVATE");
		comboDropDown.add("PROTECTED");
		
		}


		// verifica os metodos que vão ser filtrados pelo seu modifier
		checkBoxMethodMod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				int mod = 0 ;

				switch (comboDropDown.getText()) {
				case "PUBLIC":
					mod =1;
					break;
				case "PRIVATE":
					mod =2;
					break;	
				case "PROTECTED":
					mod =4;
					break;	

				default:
					break;
				}

				if(checkBoxMethodMod.getSelection()){
					verificarMetodoModifier(javaServices.getOpenedFile(),mod);
				}
			}
		});
		
		

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
					verificarMetodo(javaServices.getOpenedFile());
					//					verificaTamanhoMetodo(javaServices.getOpenedFile());
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


	private boolean checkFirstLetterLowerCase(String name){ 
		return Character.isLowerCase(name.charAt(0));
	}



	private boolean verifySize(String word){
		if(word.length()>=20){
			return true;
		}
		return false;
	}


	private boolean checkVariableUnderScore(String name){
		return (name.charAt(0)=='_');
	}

	private boolean checkVariableDollar(String name){
		return (name.charAt(0)=='$');
	}


	private boolean checkVariableLowerCase(String word){

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
				String id = node.getName().getFullyQualifiedName();

				for(ConventionService s : lista){
					if(s.verificarConvencao(id, TypeOf.CLASS).getCondition()){
						javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.CLASS).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
					}
				}

				return true;
			}
		};
		javaServices.parseFile(f, v);
	}

	private void verificarMetodo(final File f) {



		ASTVisitor v = new ASTVisitor() {



			@Override
			public boolean visit(MethodDeclaration node) {
				if(!node.isConstructor()){
					String id = node.getName().getFullyQualifiedName();

					for(ConventionService s : lista){

						if(s.verificarConvencao(id, TypeOf.METHOD).getCondition()){
							javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
						}

						if(s.verificarConvencao(id, TypeOf.METHOD).getCondition()){
							javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(),  node.getName().getLength());
						}
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

				String id = node.getName().getFullyQualifiedName();

				for(ConventionService s : lista){


					if(s.verificarConvencao(id, TypeOf.CONSTANTS).getCondition()){ //checkVariableLowerCase
						javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.CONSTANTS).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}


					if(s.verificarConvencao(id, TypeOf.CONSTANTS).getCondition()){ // checkVariableDollar
						javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.CONSTANTS).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}

					if(s.verificarConvencao(id, TypeOf.CONSTANTS).getCondition()){ //checkVariableUnderScore
						javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.CONSTANTS).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}

				}
				return true;
			};


		};
		javaServices.parseFile(f, v);
	}

	private void verificaEnum(final File f) {
		ASTVisitor v = new ASTVisitor() {

			public boolean visit(EnumConstantDeclaration node) {

				String id = node.getName().getFullyQualifiedName();

				for(ConventionService s : lista){
					if(s.verificarConvencao(id, TypeOf.ENUM).getCondition()){
						javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.ENUM).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}
				}
				return true;
			}
		};

		javaServices.parseFile(f, v);
	}


	private void verificarMetodoModifier(final File f, final int m) {


		if(!listModifier.isEmpty()){

			for(final FilterByModifier mod : listModifier){

				ASTVisitor v = new ASTVisitor() {


					@Override
					public boolean visit(MethodDeclaration node) {
						if(!node.isConstructor()){
							String id = node.getName().getFullyQualifiedName();


							if(node.getModifiers()==mod.verificarModificadorMetodo()){
								for(ConventionService s : lista){

									if(s.verificarConvencao(id, TypeOf.METHOD).getCondition()){
										javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
									}

									if(s.verificarConvencao(id, TypeOf.METHOD).getCondition()){
										javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(),  node.getName().getLength());
									}
								}					
							}




						}
						return true;
					}

				};

				javaServices.parseFile(f, v);
			}
		}else{
			
			ASTVisitor v = new ASTVisitor() {


				@Override
				public boolean visit(MethodDeclaration node) {
					if(!node.isConstructor()){
						String id = node.getName().getFullyQualifiedName();


						if(node.getModifiers()==m){
							for(ConventionService s : lista){

								if(s.verificarConvencao(id, TypeOf.METHOD).getCondition()){
									javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
								}

								if(s.verificarConvencao(id, TypeOf.METHOD).getCondition()){
									javaServices.addAnnotation(f, AnnotationType.WARNING, s.verificarConvencao(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(),  node.getName().getLength());
								}
							}					
						}




					}
					return true;
				}

			};

			javaServices.parseFile(f, v);
		}
	}

}
