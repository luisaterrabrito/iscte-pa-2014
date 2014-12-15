package pa.iscde.packagdiagram.mirba;

import pa.iscde.conventions.extensability.Cobject;
import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.TypeOf;

public class Test implements ConventionService {


	@Override
	public Cobject verifyConvention(String name, TypeOf typeof) {
		return new Cobject("Get method",typeof.equals(TypeOf.METHOD) && name.contains("get"));
	}

}
