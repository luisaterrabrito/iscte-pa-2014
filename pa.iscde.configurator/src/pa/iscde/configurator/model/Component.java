package pa.iscde.configurator.model;

public class Component {
	private String name;
	private boolean isRunning;
	public Component(String name, boolean isRunning) {
		this.name=name;
		this.isRunning=isRunning;
	}
	public String getName() {
		return name;
	}
	public boolean isRunning() {
		return isRunning;
	}

}
