package pa.iscde.commands.utils;

public enum ExtensionPointsIDS {

	ACTION_ID("pa.iscde.commands.action"), COMMAND_ID("pa.iscde.commands.command"), VIEW_ID("pt.iscte.pidesco.view"), REFRESH_EXPLORER_ID(
			"pt.iscte.pidesco.projectbrowser.refresh"), 
			REFRESH_COMMANDS_VEIW_ID("pa.iscde.commands.refreshCommands");

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
