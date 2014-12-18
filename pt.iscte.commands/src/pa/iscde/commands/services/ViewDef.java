package pa.iscde.commands.services;

final public class ViewDef {
	
	private String uniqueIdentifier;
	private String imageIdentifier;
	private String pluginContributor;
	
	/**
	 * Constructor responsible to instantiate a ViewDef object
	 * 
	 * @param uniqueIdentifier - A String of The Unique Identifier representing a specific View
	 * @param imageIdentifier - A String of The Image Identifier (icon) of the view
	 * @param pluginContributor - A String of the PluginContributor (for example, pa.iscde.commands)
	 * 
	 **/
	
	public ViewDef(String uniqueIdentifier, String imageIdentifier, String pluginContributor){
		this.uniqueIdentifier = uniqueIdentifier;
		this.imageIdentifier = imageIdentifier;
		this.pluginContributor = pluginContributor;
	}
	
	/**
	 * Method responsible to get the String of The Unique Identifier representing a specific View 
	 * @return uniqueIdentifier - String of The Unique Identifier representing a specific View
	 */
	
	public String getUniqueIdentifier(){
		return uniqueIdentifier;
	}
	
	/**
	 * Method responsible to get the String of The Image Identifier (icon) of the view 
	 * @return imageIdentifier - String of The Image Identifier (icon) of the view
	 */
	
	public String getImageIdentifier(){
		return imageIdentifier;
	}
	
	/**
	 * Method responsible to get the String of the PluginContributor (for example, pa.iscde.commands) 
	 * @return pluginContributor - String of the PluginContributor (for example, pa.iscde.commands)
	 */
	
	public String getPluginContributor(){
		return pluginContributor;
	}
	
	
	/**
	 * Method responsible to check if two objects of ViewDef class are equals.
	 * Two ViewDef are equals if the String of The Unique Identifier is also equals. 
	 */
	
	@Override
	public boolean equals(Object o){
		if( (o instanceof ViewDef) == false )
			return false;
		
		if(this == o)
			return true;
		ViewDef v = (ViewDef) o;
		
		if(v.uniqueIdentifier == this.uniqueIdentifier)
			return true;
		
		return false;
	}
	
	/**
	 * Method that return the hashCode of the current instance. A ViewDef is unique 
	 * by the return of the method hashCode of the String uniqueIdentifier. 
	 */
	
	@Override
	public int hashCode(){
		return uniqueIdentifier.hashCode();
	}
	
}
