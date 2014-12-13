package pa.iscte.mycomp;

import java.util.ArrayList;
import java.util.List;

import pa.iscde.configurator.model.interfaces.PropertyProvider;

public class ITeste implements PropertyProvider {

	@Override
	public List<String> getProperties() {
		// TODO Auto-generated method stub
		List<String> ret= new ArrayList<String>();
		ret.add("cenas");
		ret.add("Linhas");
		return ret;
	}

	@Override
	public String getValue(String property) {
		// TODO Auto-generated method stub
		if(property.equals("cenas")){
			return "Muitas";
		}
		else if (property.equals("Linhas")) {
			return "1000!!";
		}
		else{
			return "nada";
		}
	}

}
