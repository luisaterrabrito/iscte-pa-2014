package pa.iscde.tasklist;

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

}
