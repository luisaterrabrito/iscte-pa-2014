package pa.iscde.conventions.extensability;



public interface ConventionService {
	
	
	/**
	 * Utiliza a convenção que a equipa deseja.
	 * 
	 * @param name - ID(Nome) do que vai ser verificado.
	 * @param to - Tipo de Enumerado definido pelo TyeOf (Método, Classe, Constantes, Enumerados).
	 * @return Cobject - Um objecto do tipo de convenção que recebe um Aviso e uma Convenção
	 * 
	 * Exemplo:
	 * 
	 * @Override
		public Cobject verificarConvencao(String name, TypeOf to) {
		return new Cobject("Método contém underscore" ,to.equals(TypeOf.METHOD) && name.contains("_"));
		}
	 */
	public Cobject verificarConvencao(String name, TypeOf to);
	
	
}
