package pa.iscde.commands.services;

public class ViewDef {
	
	private String uniqueIdentifier;
	private String imageIdentifier;
	private String pluginContributor;
	
	public ViewDef(String uniqueIdentifier, String imageIdentifier, String pluginContributor){
		this.uniqueIdentifier = uniqueIdentifier;
		this.imageIdentifier = imageIdentifier;
		this.pluginContributor = pluginContributor;
	}
	
	public String getUniqueIdentifier(){
		return uniqueIdentifier;
	}
	
	public String getImageIdentifier(){
		return imageIdentifier;
	}
	
	public String getPluginContributor(){
		return pluginContributor;
	}
	
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
	
	@Override
	public int hashCode(){
		return uniqueIdentifier.hashCode();
	}
	
}
