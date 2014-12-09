package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;

public class DropMethod extends DropAble {

	private HashMap<String, String> params;
	private boolean isConstructor;

	public DropMethod(ASTNode node, String name, boolean isConstructor) {
		super(node, name);
		this.isConstructor = isConstructor;
		params = new HashMap<>();
	}

	public void addParam(String name, String type) {
		params.put(name, type);
	}

	public String getParamType(String name) {
		return params.get(name);
	}

	public boolean isConstructor() {
		return isConstructor;
	}

}
