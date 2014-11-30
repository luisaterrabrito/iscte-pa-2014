package pa.iscde.commands.utils;

public enum ExtensionPointsIDS {

	ACTION_ID("pa.iscde.commands.action"), COMMAND_ID("pa.iscde.commands.command");

	private String ID;

	private ExtensionPointsIDS(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	@Override
	public String toString() {
		return getID();
	}

}
