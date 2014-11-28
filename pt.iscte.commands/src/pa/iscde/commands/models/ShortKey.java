package pa.iscde.commands.models;

final public class ShortKey {

	private String description;

	public ShortKey(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getKeyString() {
		return "CRTL+ALT+R";
	}

}
