package pa.iscde.packagediagram.asdss.internal;

import java.lang.reflect.Modifier;

import pa.iscde.conventions.extensability.FilterByModifier;

public class teste implements FilterByModifier {

	@Override
	public int verificarModificadorMetodo() {
		// TODO Auto-generated method stub
		return Modifier.PRIVATE;
	}

}
