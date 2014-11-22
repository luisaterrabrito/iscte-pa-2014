package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;
import java.util.HashSet;

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

		System.out.println("DropAble.setModifiers()");

		// TODO: Add Modifiers
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
