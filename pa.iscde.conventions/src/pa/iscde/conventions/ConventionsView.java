package pa.iscde.conventions;



import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
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
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class ConventionsView implements PidescoView {


	private JavaEditorServices javaServices;

	private static final String EXT_POINT_CONVENTION = "pa.iscde.conventions.conventionservice";
	private static final String EXT_POINT_MODIFIER = "pa.iscde.conventions.filterbymodifier";

	private LinkedList<ConventionService> lista;
	private LinkedList<FilterByModifier> listModifier;
	private ConventionVisitor visitor;
	private LinkedList<Button> botao = new LinkedList<Button>();


	public ConventionsView() {
		Bundle bundle = FrameworkUtil.getBundle(ConventionsView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
	}

	
	/**
	 * Creates the Graphical User Interface.
	 *
	 * @author Vitor Sousa - Nº 33593
	 * @author Pedro Cananão - Nº 33585
	 * @author PA - MEI
	 */
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

		listModifier = new LinkedList<FilterByModifier>();
		lista = new LinkedList<ConventionService>();
		lista.add(cs);


		visitor= new ConventionVisitor(lista, listModifier, javaServices);

		
		RowLayout rowLayout = new RowLayout();
		rowLayout.type = SWT.VERTICAL;
		viewArea.setLayout(rowLayout);
		final Label label = new Label(viewArea, SWT.NONE);
		label.setText("Choose one of the options to validate the Class:");
		
		//Verificar classes.
		final Button checkBoxClass = new Button(viewArea, SWT.CHECK);
		checkBoxClass.setSize(10, 20);
		checkBoxClass.setText("Verify Class");
		//Verificar Metodos
		final Button checkBoxMethod = new Button(viewArea, SWT.CHECK);
		checkBoxMethod.setSize(10, 20);
		checkBoxMethod.setText("Verify All Methods");
		//Verificar Constantes
		final Button checkBoxConstant = new Button(viewArea, SWT.CHECK);
		checkBoxConstant.setSize(10, 20);
		checkBoxConstant.setText("Verify Constants/Variables (LowerCase, '$' , '_' )");
		//Verificar Enumerados
		final Button checkBoxEnum = new Button(viewArea, SWT.CHECK);
		checkBoxEnum.setSize(10, 20);
		checkBoxEnum.setText("Verify Enum");

		final Button checkBoxMethodMod = new Button(viewArea, SWT.CHECK);
		checkBoxMethodMod.setSize(10, 20);


		if(listModifier.isEmpty()){
			checkBoxMethodMod.setText("Filtrar Método por Modifier igual a : ");

		checkBoxMethodMod.setText("Filter Method by Modifier equals to : ");

		}else{
			checkBoxMethodMod.setText("Filter Method by Modifier");
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
					mod = 1;
					break;
				case "PRIVATE":
					mod = 2;
					break;	
				case "PROTECTED":
					mod = 4;
					break;	

				default:
					break;
				}

				if(checkBoxMethodMod.getSelection()){
					visitor.visitorOfModifier(javaServices.getOpenedFile(),mod,lista.indexOf(lista.getFirst()) );
				}
			}
		});


		
		handleConventionExtension(viewArea);
		handleFilterByModExtension();



		//verificar classes.
		checkBoxClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxClass.getSelection()){
					visitor.visitorOfClass(javaServices.getOpenedFile(),lista.indexOf(lista.getFirst()));
				}
			}
		});



		//verificar Metodos
		checkBoxMethod.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				if(checkBoxMethod.getSelection()){
					visitor.visitorOfMethod(javaServices.getOpenedFile(),lista.indexOf(lista.getFirst()));
					//					verificaTamanhoMetodo(javaServices.getOpenedFile());
				}
			}
		});





		//Verificar Constantes
		checkBoxConstant.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxConstant.getSelection()){
					visitor.visitorOfConstants(javaServices.getOpenedFile(),lista.indexOf(lista.getFirst()));
				}
			}
		});


		//Verificar Enumerados
		checkBoxEnum.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBoxEnum.getSelection()){
					visitor.visitorOfEnum(javaServices.getOpenedFile(),lista.indexOf(lista.getFirst()));
				}
			}
		});



	}


	private void handleFilterByModExtension() {
		IExtensionRegistry regMod = Platform.getExtensionRegistry();

		IConfigurationElement[] extensionsMod = regMod.getConfigurationElementsFor(EXT_POINT_MODIFIER);
		System.out.println(extensionsMod.length);
		for(IConfigurationElement ext : extensionsMod){


			try {

				FilterByModifier m = (FilterByModifier) ext.createExecutableExtension("filterbymodifier");
				listModifier.add(m);

			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		
		}
		
	}


	private void handleConventionExtension(Composite viewArea) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(EXT_POINT_CONVENTION);

			for(IConfigurationElement ext : extensions){
				try {
					final ConventionService s = (ConventionService) ext.createExecutableExtension("class");
					lista.add(s);
					final String type = ext.getAttribute("Type");
					final Button newButton = new Button(viewArea, SWT.CHECK);  
					newButton.setText(ext.getAttribute("ConventionName"));

					// save the button
					botao.add(newButton);
					newButton.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							switch (type) {
							case "CLASS":
								visitor.visitorOfClass(javaServices.getOpenedFile(),getElementList(lista, s));

								break;
							case "METHOD":
								visitor.visitorOfMethod(javaServices.getOpenedFile(),getElementList(lista, s));

								break;
							case "CONSTANT":
								visitor.visitorOfConstants(javaServices.getOpenedFile(),getElementList(lista, s));

								break;
							case "ENUM":
								visitor.visitorOfEnum(javaServices.getOpenedFile(),getElementList(lista, s));

								break;

							default:
								break;
							}


						}

					});
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
				
			}
			
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


	private int getElementList(LinkedList<ConventionService> cs, ConventionService s){
		int id = 0;

		for(int i =0 ; i!= cs.size();i++){
			if(cs.get(i).equals(s)){
				return id = i;
			}

		}


		return id;


	}


}
