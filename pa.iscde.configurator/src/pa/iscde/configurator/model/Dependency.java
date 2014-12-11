package pa.iscde.configurator.model;

public class Dependency {
	
	private Component mainComponent;
	private Component dependantComponent;
	private String extensionPointId;

	public Dependency(Component mainComponent, Component dependantComponent,String extensionPointId) {
		this.mainComponent=mainComponent;
		this.dependantComponent=dependantComponent;
		this.extensionPointId=extensionPointId;
	}
	public Component getMainComponent() {
		return mainComponent;
	}
	public Component getDependantComponent() {
		return dependantComponent;
	}
	public String getExtensionPointId() {
		return extensionPointId;
	}
	
	

}
