package pa.iscde.conventions;

import java.io.File;
import java.util.LinkedList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.FilterByModifier;
import pa.iscde.conventions.extensability.TypeOf;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class ConventionVisitor {
	
	private ASTVisitor v ;
	private LinkedList<ConventionService> lista;
	private LinkedList<FilterByModifier> listaModifier;
	JavaEditorServices javaServices;
	
	/**
	 * Initializes the convention of the visitor with the lists and the java service.
	 * @param lista - receives a list of the extension point ConventionService
	 * @param listaModifier - receives the list of the extension point FIlterByModifier
	 * @param javaServices - receives the java service.
	 */
	public ConventionVisitor(LinkedList<ConventionService> lista,LinkedList<FilterByModifier> listaModifier, JavaEditorServices javaServices ) {
		this.lista = lista;
		this.listaModifier=listaModifier;
		this.javaServices=javaServices;
	}
	
	
	/**
	 * Uses the AST Visitor to check if the name of the class is according to the conventions.
	 * @param f - Receives the opened File
	 * @param identifier - Receives the identifier to obtain the convention service of the list.
	 */
	public void visitorOfClass(final File f,final int identifier) {
		v = new ASTVisitor() {
			@Override
			public boolean visit(TypeDeclaration node) {
				String id = node.getName().getFullyQualifiedName();

					if(lista.get(identifier).verifyConvention(id, TypeOf.CLASS).getCondition()){
						javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.CLASS).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
					}
				

				return true;
			}
		};
		javaServices.parseFile(f, v);
	}
	
	/**
	 * Uses the AST Visitor to check if the Methods are according to the conventions.
	 * @param f - Receives the opened File
	 * @param identifier - Receives the identifier to obtain the convention service of the list.
	 */
	public void visitorOfMethod(final File f, final int identifier){
		v = new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
				if(!node.isConstructor()){
					String id = node.getName().getFullyQualifiedName();

	
						if(lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getCondition()){
							javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
						}

						if(lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getCondition()){
							javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(),  node.getName().getLength());
						}
					
				}
				return true;
			}

		};

		javaServices.parseFile(f, v);
	}
	
	
	/**
	 * Uses the AST Visitor to check if the constants are according to the conventions.
	 * @param f - Receives the opened File
	 * @param identifier - Receives the identifier to obtain the convention service of the list.
	 */
	public void visitorOfConstants(final File f, final int identifier){
		v = new ASTVisitor() {

			public boolean visit(VariableDeclarationFragment node) {

				String id = node.getName().getFullyQualifiedName();

			
					if(lista.get(identifier).verifyConvention(id, TypeOf.CONSTANTS).getCondition()){ //checkVariableLowerCase
						javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.CONSTANTS).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}
					if(lista.get(identifier).verifyConvention(id, TypeOf.CONSTANTS).getCondition()){ // checkVariableDollar
						javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.CONSTANTS).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}
					if(lista.get(identifier).verifyConvention(id, TypeOf.CONSTANTS).getCondition()){ //checkVariableUnderScore
						javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.CONSTANTS).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}

				
				return true;
			};


		};
		javaServices.parseFile(f, v);
	}
	
	
	/**
	 * Uses the AST Visitor to check if the Enumerate is according to the conventions.
	 * @param f - Receives the opened File
	 * @param identifier - Receives the identifier to obtain the convention service of the list.
	 */
	public void visitorOfEnum(final File f, final int identifier){
		v = new ASTVisitor() {

			public boolean visit(EnumConstantDeclaration node) {

				String id = node.getName().getFullyQualifiedName();

					if(lista.get(identifier).verifyConvention(id, TypeOf.ENUM).getCondition()){
						javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.ENUM).getWarning(),
								node.getName().getStartPosition(), node.getName().getLength());
					}
				
				return true;
			}
		};

		javaServices.parseFile(f, v);
	}
	
	/**
	 * Uses the AST Visitor to check if the methods that are being filtered with the modifier are according to the conventions
	 * @param f - Receives the opened File
	 * @param m - Receives the Modifier
	 * @param identifier - Receives the identifier to obtain the convention service of the list.
	 * 
	 */
	public void visitorOfModifier(final File f, final int m, final int identifier){
		
		if(!listaModifier.isEmpty()){

			for(final FilterByModifier mod : listaModifier){

				 v = new ASTVisitor() {


					@Override
					public boolean visit(MethodDeclaration node) {
						if(!node.isConstructor()){
							String id = node.getName().getFullyQualifiedName();


							if(node.getModifiers()==mod.verificarModificadorMetodo()){
								

									if(lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getCondition()){
										javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
									}

									if(lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getCondition()){
										javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(),  node.getName().getLength());
									}
													
							}




						}
						return true;
					}

				};

				javaServices.parseFile(f, v);
			}
		}else{
			
			 v = new ASTVisitor() {


				@Override
				public boolean visit(MethodDeclaration node) {
					if(!node.isConstructor()){
						String id = node.getName().getFullyQualifiedName();


						if(node.getModifiers()==m){
			
								if(lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getCondition()){
									javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(), node.getName().getLength());
								}

								if(lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getCondition()){
									javaServices.addAnnotation(f, AnnotationType.WARNING, lista.get(identifier).verifyConvention(id, TypeOf.METHOD).getWarning(), node.getName().getStartPosition(),  node.getName().getLength());
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
