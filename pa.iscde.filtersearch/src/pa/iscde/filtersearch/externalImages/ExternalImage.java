package pa.iscde.filtersearch.externalImages;

public class ExternalImage {
	
	private String imageName;
	private String plugin;
	
	
	public ExternalImage(String imageName, String plugin) {
		super();
		this.imageName = imageName;
		this.plugin = plugin;
	}

	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getPlugin() {
		return plugin;
	}
	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}
}