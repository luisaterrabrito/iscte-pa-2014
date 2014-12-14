package pa.iscde.configurator.model;
/**
 * This class represents a dependency between to components(bundles), associated to some extension point
 * that exists on the mainComponent
 * 
 * It is created by the class Constructor.java in the package pa.iscde.configurator.controller
 * 
 */
public class Dependency {
	
	private Component mainComponent;
	private Component dependantComponent;
	private String extensionPointId;
	/**
	 * Class constructor
	 * Creates a dependency between two components specifying the mainComponent(component that
	 * has the extension Point), the dependantComponent(component that creates an extension to 
	 * the extension point and a String with the id of the extension point the this dependency 
	 * is about.
	 *  
	 */
	public Dependency(Component mainComponent, Component dependantComponent,String extensionPointId) {
		this.mainComponent=mainComponent;
		this.dependantComponent=dependantComponent;
		this.extensionPointId=extensionPointId;
	}
	/**
	 * Get the main component
	 * @return an object of the class Component that represents the main component
	 */
	public Component getMainComponent() {
		return mainComponent;
	}

	/**
	 * Get the dependant component
	 * @return an object of the class Component that represents the dependant component
	 */
	public Component getDependantComponent() {
		return dependantComponent;
	}

	/**
	 * Get the id of the extension point that this dependency is about.
	 * @return a String with the extension point id
	 */
	public String getExtensionPointId() {
		return extensionPointId;
	}
	
	

}
