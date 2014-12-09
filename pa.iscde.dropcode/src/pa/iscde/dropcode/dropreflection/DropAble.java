package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;
import java.util.HashSet;

import pa.iscde.dropcode.dropreflection.DropModifier.DM_Others;
import pa.iscde.dropcode.dropreflection.DropModifier.DM_Visibility;

public class DropAble {
	private String name;
	private String type;
	private HashMap<String, DropAnnotation> annotations;
	private HashSet<DM_Others> modifiers;
	private DM_Visibility visibility_modifier;

	public DropAble(String name) {
		this.name = name;
		modifiers = new HashSet<>();
		annotations = new HashMap<>();
	}

	public void setModifiers(int modsBitwise) {
		for (DM_Others mod : DM_Others.values()) {
			if (mod.isPresent(modsBitwise))
				modifiers.add(mod);
		}
	}

	public void setVisibilityModifier(int modsBitwise) {
		for (DM_Visibility mod : DM_Visibility.values()) {
			if (mod.isPresent(modsBitwise)) {
				this.visibility_modifier = mod;
				return;
			}
		}
	}

	public String name() {
		return name;
	}

	public DM_Visibility getVisibilityModifier() {
		return visibility_modifier;
	}

	public boolean isModifierPresent(DM_Others modifier) {
		return modifiers.contains(modifier);
	}

	public DropAnnotation getAnnotation(String annotation) {
		return annotations.get(annotation);
	}

	public String getType() {
		return type;
	}

	public void removeModifier(DM_Others mod) {
		modifiers.remove(mod);
	}
}
