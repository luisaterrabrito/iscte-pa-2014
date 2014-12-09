package pa.iscde.dropcode.dropreflection;

import java.util.HashMap;

public class DropMethod extends DropAble {

	private HashMap<String, String> params;
	private boolean isConstructor;

	public DropMethod(String name, boolean isConstructor) {
		super(name);
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
