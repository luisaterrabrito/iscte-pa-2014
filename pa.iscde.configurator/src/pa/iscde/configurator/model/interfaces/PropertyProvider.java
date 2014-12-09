package pa.iscde.configurator.model.interfaces;

import java.util.List;

public interface PropertyProvider {

	List<String> getProperties();
	
	String getValue(String property);
}
