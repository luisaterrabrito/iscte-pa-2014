package pa.iscde.conventions;

import org.eclipse.jdt.core.dom.Modifier;

import pa.iscde.conventions.extensability.FilterByModifier;

public class testeMetodo implements FilterByModifier{

	@Override
	public int verificarModificadorMetodo() {
		
		return Modifier.PRIVATE;
		
		
	}

	
	
}
