package pa.iscde.commands.external.services;

import pa.iscde.commands.utils.Labels;
import pa.iscde.conventions.extensability.Cobject;
import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.TypeOf;

public class ConstantsStartWord implements ConventionService {

	@Override
	public Cobject verificarConvencao(String name, TypeOf typeof) {
		return new Cobject(Labels.CONSTANTSSTARTWORD,
				typeof.equals(TypeOf.CONSTANTS) && name.contains("CONST_"));
	}

}
