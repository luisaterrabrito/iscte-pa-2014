package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.FieldDeclaration;

public class DropAble {
	private String name;
	private HashMap<String, DropAnnotation> annotations;
	private HashSet<DropModifier> modifiers;

	public DropAble() {
		modifiers = new HashSet<>();
		annotations = new HashMap<>();
	}

	public String name() {
		return name;
	}

	public void setModifiers(int modifiers) {
		if((modifiers & 10) != 0 ){
			System.out.println();
		}
		if((modifiers & 001) != 0 ){
		
		}
	}
	
	public void removeModifier(DropModifier modifier){
		modifiers.remove(modifier);
	}

	public DropModifier getVisibilityModifier() {
		for (DropModifier m : modifiers) {
			if (m.id == 0)
				return m;
		}
		return DropModifier.PACKAGE_PRIVATE;
	}

	public boolean isModifierPresent(DropModifier modifier) {
		return modifiers.contains(modifier);
	}

	public DropAnnotation getAnnotation(String annotation) {
		return annotations.get(annotation);
	}
}
