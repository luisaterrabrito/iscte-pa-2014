package pa.iscde.tasklist.extensibility;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class Category {
	
	private String tag;
	private String name;
	private Image icon;
	private Color color;
	
	public Category(String tag, String name, Image icon, Color color){
		this.tag = tag;
		this.name = name;
		this.icon = icon;
		this.color = color;
	}
	
	public Image getIcon() {
		return icon;
	}
	
	public String getTag() {
		return tag;
	}
	
	public Color getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
}
