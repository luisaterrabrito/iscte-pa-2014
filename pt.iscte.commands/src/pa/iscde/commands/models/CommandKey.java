package pa.iscde.commands.models;

final public class CommandKey {

	private String name;
	private String view;
	private boolean ctrl_key;
	private boolean alt_key;
	private char key;

	public CommandKey(String view, boolean ctrl_key, boolean alt_key, char key) {
		this.ctrl_key = ctrl_key;
		this.alt_key = alt_key;
		this.key = Character.toLowerCase(key);
		this.view = view;
	}

	public CommandKey(String name, String view, boolean ctrl_key,
			boolean alt_key, char key) {
		this(view, ctrl_key, alt_key, key);
		this.name = name;
	}

	public String getCommandName() {
		return name;
	}

	public boolean usesCtrl() {
		return ctrl_key;
	}

	public boolean usesAlt() {
		return alt_key;
	}

	public char usesKey() {
		return key;
	}

	public String getContext() {
		return view;
	}

	public void setAltKey(boolean alt_key) {
		this.alt_key = alt_key;
	}

	public void setCtrlKey(boolean ctrl_key) {
		this.ctrl_key = ctrl_key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return ((short) view.hashCode())
				^ ((short) Boolean.valueOf(ctrl_key).hashCode())
				^ ((short) Boolean.valueOf(alt_key).hashCode())
				^ ((short) Character.valueOf(key).hashCode());
	}

	@Override
	public boolean equals(Object o) {

		if (o == null)
			return false;

		if (this == o)
			return true;

		CommandKey command = (CommandKey) o;
		if (view.equals(command.view) && ctrl_key == command.ctrl_key
				&& alt_key == command.alt_key && key == command.key)
			return true;

		return false;

	}

	@Override
	public String toString() {
		String result = "";
		if (ctrl_key)
			result = "CTRL + ";

		if (alt_key && !ctrl_key)
			result = "ALT + ";
		else if (alt_key && ctrl_key)
			result = result + "ALT + ";

		return result + Character.toUpperCase(key);
	}

}