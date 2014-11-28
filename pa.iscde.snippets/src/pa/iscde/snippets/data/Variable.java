package pa.iscde.snippets.data;

public class Variable {
	private String name;
	private String type;
	private String value;
	
	public Variable(String name, String type){
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
}
