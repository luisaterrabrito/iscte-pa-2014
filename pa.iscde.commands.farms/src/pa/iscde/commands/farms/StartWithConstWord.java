package pa.iscde.commands.farms;

import pa.iscde.commands.farms.Labels;
import pa.iscde.conventions.extensability.Cobject;
import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.TypeOf;

public class StartWithConstWord implements ConventionService {

	@Override
	public Cobject verifyConvention(String name, TypeOf typeof) {
		return new Cobject(Labels.CONSTANTSSTARTWORD,
				typeof.equals(TypeOf.CONSTANTS) && !name.contains("CONST_"));
	}

}
