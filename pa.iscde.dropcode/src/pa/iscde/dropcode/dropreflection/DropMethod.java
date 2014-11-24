package pa.iscde.dropcode.dropreflection;

public class DropMethod extends DropAble {

	private boolean isConstructor;

	public DropMethod(String name, boolean isConstructor) {
		super(name);
		this.isConstructor = isConstructor;
	}

	public boolean isConstructor() {
		return isConstructor;
	}

}
