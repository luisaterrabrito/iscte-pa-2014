package pa.iscde.dropcode.dropreflection;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DropClass {

	private HashMap<String, DropField> fields;
	private HashMap<String, DropMethod> methods;

	public DropClass(JavaEditorServices javaEditor) {

		// CONSTRUCT THE JAVA CLASS //

		ASTVisitor visitor = new ASTVisitor() {

			@Override
			public boolean visit(FieldDeclaration field) {

				// ADD FIELDS //
				DropField newField = new DropField();
				newField.setModifiers(field.getModifiers());
				fields.put(newField.name(), newField);
				return false;
			}

			@Override
			public boolean visit(MethodDeclaration method) {
				// ADD METHODS //
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

}
