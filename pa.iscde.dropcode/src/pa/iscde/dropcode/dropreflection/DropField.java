package pa.iscde.dropcode.dropreflection;

public class DropField extends DropAble {

	private String type;

	public DropField(String name, String type) {
		super(name);
		this.type = type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
