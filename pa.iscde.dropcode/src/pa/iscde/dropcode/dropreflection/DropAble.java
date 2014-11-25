package pa.iscde.dropcode.dropreflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

	public void setDeclaration(String declaration) {
		List<String> field = Arrays.asList(declaration.toString().split(" "));

		// VISIBILITY MODIFIERS
		FOR: for (DM_Visibility mod : DM_Visibility.values()) {
			if (field.contains(mod.toString().toLowerCase())){
				setVisibilityModifier(mod);
				break FOR;
			}
		}

		// OTHER MODIFIERS
		for (DM_Others mod : DM_Others.values()) {
			if (field.contains(mod.toString().toLowerCase()))
				addModifier(mod);
		}
	}

	public String name() {
		return name;
	}

	public void setVisibilityModifier(DM_Visibility vis_mod) {
		this.visibility_modifier = vis_mod;
	}

	public DM_Visibility getVisibilityModifier() {
		return visibility_modifier;
	}

	public void addModifier(DM_Others mod) {
		modifiers.add(mod);
	}

	public void removeModifier(DM_Others modifier) {
		modifiers.remove(modifier);
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
}
