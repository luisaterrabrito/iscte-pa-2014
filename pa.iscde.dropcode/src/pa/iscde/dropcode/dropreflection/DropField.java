package pa.iscde.dropcode.dropreflection;

import org.eclipse.jdt.core.dom.ASTNode;

public class DropField extends DropAble {

	private String type;

	public DropField(ASTNode node, String name, String type) {
		super(node, name);
		this.type = type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
