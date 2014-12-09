package pa.iscde.configurator.model.interfaces;

import java.util.List;
import java.util.Vector;

public class PropertyProviderImpl implements PropertyProvider {
	Vector<String> ret;

	public PropertyProviderImpl() {
		ret = new Vector<String>();
		ret.add("a");
		ret.add("b");
	}

	@Override
	public List<String> getProperties() {
		// TODO Auto-generated method stub
		return ret;
	}

	@Override
	public String getValue(String property) {
		// TODO Auto-generated method stub

		return "?";

	}
}
