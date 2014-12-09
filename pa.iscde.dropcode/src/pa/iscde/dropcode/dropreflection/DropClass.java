package pa.iscde.dropcode.dropreflection;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DropClass {

	private HashMap<String, DropField> fields;
	private HashMap<String, DropMethod> constructors;
	private HashMap<String, DropMethod> methods;

	public DropClass(JavaEditorServices javaEditor) {

		fields = new HashMap<>();
		constructors = new HashMap<>();
		methods = new HashMap<>();

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

				fields.put(newField.name(), newField);
				return true;
			}

			@Override
			public boolean visit(MethodDeclaration method) {
				DropMethod newMethod = new DropMethod(method.getName()
						.getFullyQualifiedName(), method.isConstructor());

				// MODS
				newMethod.setModifiers(method.getModifiers());

				@SuppressWarnings("unchecked")
				List<SingleVariableDeclaration> params = (List<SingleVariableDeclaration>) method
						.getStructuralProperty(MethodDeclaration.PARAMETERS_PROPERTY);
				for (SingleVariableDeclaration var : params) {

					newMethod.addParam(var.getName().toString(), var.getType()
							.toString());
				}

				if (method.isConstructor()) {
					constructors.put(newMethod.name(), newMethod);
				} else {
					methods.put(newMethod.name(), newMethod);
				}
				return false;
			}
		};
		javaEditor.parseFile(javaEditor.getOpenedFile(), visitor);

	}

	public DropField getField(String name) {
		return fields.get(name);
	}

	public Collection<DropField> getFields() {
		return fields.values();
	}

	public DropMethod getMethod(String name) {
		return methods.get(name);
	}

	public Collection<DropMethod> getConstructors() {
		return constructors.values();
	}

	public Collection<DropMethod> getMethods() {
		return methods.values();
	}

}
