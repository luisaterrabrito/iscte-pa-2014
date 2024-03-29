package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTNode;

import pa.iscde.dropcode.dropreflection.DropModifier.DM_Others;
import pa.iscde.dropcode.dropreflection.DropModifier.DM_Visibility;

public class DropAble {

	ASTNode node;

	private String name;
	private HashMap<String, DropAnnotation> annotations;
	private HashSet<DM_Others> modifiers;
	private DM_Visibility visibility_modifier;
	private String type;

	public DropAble(ASTNode node, String name, String type) {
		this.name = name;
		this.type = type;
		modifiers = new HashSet<>();
		annotations = new HashMap<>();
		this.node = node;
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

	public void removeModifier(DM_Others mod) {
		modifiers.remove(mod);
	}

	public ASTNode getNode() {
		return node;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
