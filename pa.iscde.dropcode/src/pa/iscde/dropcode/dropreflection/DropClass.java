package pa.iscde.dropcode.dropreflection;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DropClass {

	private LinkedList<DropField> fields;
	private LinkedList<DropMethod> constructors;
	private LinkedList<DropMethod> methods;

	public DropClass(JavaEditorServices javaEditor) {

		fields = new LinkedList<>();
		constructors = new LinkedList<>();
		methods = new LinkedList<>();

		// CONSTRUCT THE JAVA CLASS //

		ASTVisitor visitor = new ASTVisitor() {

			@Override
			public boolean visit(FieldDeclaration field) {
				DropField newField = new DropField(field.fragments().get(0)
						.toString());
				// MODS
				newField.setModifiers(field.getModifiers());
				newField.setVisibilityModifier(field.getModifiers());

				// TODO 1
				field.getStartPosition();
				field.getLength();

				fields.add(newField);
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration method) {
				DropMethod newMethod = new DropMethod(method.getName()
						.getFullyQualifiedName(), method.isConstructor());

				// MODS
				newMethod.setModifiers(method.getModifiers());
				newMethod.setVisibilityModifier(method.getModifiers());

				@SuppressWarnings("unchecked")
				List<SingleVariableDeclaration> params = (List<SingleVariableDeclaration>) method
						.getStructuralProperty(MethodDeclaration.PARAMETERS_PROPERTY);
				for (SingleVariableDeclaration var : params) {

					newMethod.addParam(var.getName().toString(), var.getType()
							.toString());
				}

				if (method.isConstructor()) {
					constructors.add(newMethod);
				} else {
					methods.add(newMethod);
				}
				return false;
			}
		};
		javaEditor.parseFile(javaEditor.getOpenedFile(), visitor);

	}

	public Collection<DropField> getFields() {
		return new LinkedList<>(fields);
	}

	public Collection<DropMethod> getConstructors() {
		return new LinkedList<>(constructors);
	}

	public Collection<DropMethod> getMethods() {
		return new LinkedList<>(methods);
	}

}
