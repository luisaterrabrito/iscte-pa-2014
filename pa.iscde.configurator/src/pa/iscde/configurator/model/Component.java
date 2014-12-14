package pa.iscde.configurator.model;
/**
 * This class represents a component(bundle) that is running, has a view and is
 * related to the project to represent on ConfiguratorView 
 * 
 * It is created by the class Constructor.java in the package pa.iscde.configurator.controller
 * 
 */
public class Component {
	private String name;
	private boolean isRunning;
	/**
	 * Class constructor
	 * Creates a component specifying id name and a boolean that indicates if he's running or not
	 * this boolean exists to be possible, in the future, to create components that aren't running
	 * and differentiate them from those who are running
	 *  
	 */
	public Component(String name, boolean isRunning) {
		this.name=name;
		this.isRunning=isRunning;
	}
	/**
	 * Get the name of the component
	 * @return a String with the name of the component
	 */
	public String getName() {
		return name;
	}
	/**
	 * Check if the component is running or not
	 * @return true if the component is running and false if it isn't
	 */
	public boolean isRunning() {
		return isRunning;
	}

}

