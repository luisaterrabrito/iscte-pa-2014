package pa.iscde.umldiagram.nbcot;

import java.util.List;
import java.util.Vector;

import pa.iscde.configurator.model.interfaces.PropertyProvider;

public class Teste implements PropertyProvider {

	private Vector<String> ret;

	public Teste() {
		ret = new Vector<String>();
		ret.add("dor");
		ret.add("ui");
	}

	@Override
	public List<String> getProperties() {
		// TODO Auto-generated method stub
		return ret;
	}

	@Override
	public String getValue(String property) {
		// TODO Auto-generated method stub
		return "teste";
	}

}
