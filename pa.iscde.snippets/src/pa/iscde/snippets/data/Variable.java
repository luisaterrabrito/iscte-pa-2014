package pa.iscde.snippets.data;

public class Variable {
	private String substituteToken;
	private String name;
	private String type;
	private String value;

	public Variable(String substituteToken, String name, String type) {
		super();
		this.substituteToken = substituteToken;
		this.name = name;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSubstituteToken() {
		return substituteToken;
	}
}
