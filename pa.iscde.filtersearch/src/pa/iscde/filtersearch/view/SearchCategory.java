package pa.iscde.filtersearch.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

/**
 * Uma SearchCategory vai corresponder a nada mais nada menos que um projecto que implemente a interface SearchProvider.
 * 
 * @authors LuisMurilhas & DavidAlmas
 */

public class SearchCategory {
	String name;
	Image icon;
	List<Object> hits = new ArrayList<>();

	SearchCategory(String name, Image icon) {
		this.name = name;
		this.icon = icon;
	}

	@Override
	public String toString() {
		return name; 
	}

	public Image getIcon(){
		return icon;
	}
	
	public List<Object> getHits(){
		return hits;
	}
}