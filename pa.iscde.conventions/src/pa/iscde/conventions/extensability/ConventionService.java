package pa.iscde.conventions.extensability;

import pa.iscde.conventions.Cobject;


public interface ConventionService {
	
	
	/**
	 * Utiliza a convenção que a equipa deseja.
	 * 
	 * @param name - ID(Nome) do que vai ser verificado.
	 * @param to - Tipo de Enumerado definido pelo TyeOf (Método, Classe, Constantes, Enumerados).
	 * @return Cobject - Um objecto do tipo de convenção que recebe um Aviso e uma Convenção
	 */
	public Cobject verificarConvencao(String name, TypeOf to);
	
	
}
