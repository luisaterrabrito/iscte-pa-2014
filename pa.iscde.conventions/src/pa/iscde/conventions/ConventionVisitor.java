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
	
	
	public ConventionVisitor(LinkedList<ConventionService> lista,LinkedList<FilterByModifier> listaModifier, JavaEditorServices javaServices ) {
		this.lista = lista;
		this.listaModifier=listaModifier;
		this.javaServices=javaServices;
	}
	
	public void visitorOfClass(final File f) {
		v = new ASTVisitor() {
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
	
	
	public void visitorOfMethod(final File f){
		v = new ASTVisitor() {
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
	
	public void visitorOfConstants(final File f){
		v = new ASTVisitor() {

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
	
	public void visitorOfEnum(final File f){
		v = new ASTVisitor() {

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
	
	
	public void visitorOfModifier(final File f, final int m){
		
		if(!listaModifier.isEmpty()){

			for(final FilterByModifier mod : listaModifier){

				 v = new ASTVisitor() {


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
			
			 v = new ASTVisitor() {


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
