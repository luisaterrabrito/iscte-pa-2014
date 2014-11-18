package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;
import java.util.HashSet;

public class DropAble {
	private String name;
	private HashMap<String, DropAnnotation> annotations;
	private HashSet<DropModifier> modifiers;

	public String name() {
		return name;
	}

	public void setModifiers(int modifiers) {
		
		System.out.println("DropAble.setModifiers()");
		
		// TODO ADD MODIFIERS
	}

	public boolean isModifierPresent(DropModifier modifier) {
		return modifiers.contains(modifier);
	}

	public DropAnnotation getAnnotation(String annotation) {
		return annotations.get(annotation);
	}
}
