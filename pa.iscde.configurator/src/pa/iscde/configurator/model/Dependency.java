package pa.iscde.configurator.model;

public class Dependency {
	
	private Component mainComponent;
	private Component dependantComponent;

	public Dependency(Component mainComponent, Component dependantComponent) {
		this.mainComponent=mainComponent;
		this.dependantComponent=dependantComponent;
	}
	public Component getMainComponent() {
		return mainComponent;
	}
	public Component getDependantComponent() {
		return dependantComponent;
	}
	

}
