package pa.iscde.conventions;

import pa.iscde.conventions.extensability.Cobject;
import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.TypeOf;

public class teste implements ConventionService {

	@Override
	public Cobject verificarConvencao(String name, TypeOf to) {
		return new Cobject("CANANAOO", to.equals(TypeOf.METHOD) && name.contains("_"));
	}

}
