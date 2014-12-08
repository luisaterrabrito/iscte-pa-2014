package pa.iscde.conventions.extensability;



public interface ConventionService {
	
	
	/**
	 * Utiliza a convenção que a equipa deseja através da classe Cobject.
	 * 
	 * @param name - ID(Nome) do que vai ser verificado.
	 * @param typeof - Tipo de Enumerado definido pelo TypeOf (Método, Classe, Constantes, Enumerados).
	 * @return Cobject - Um objecto do tipo de convenção que recebe um Aviso e uma Convenção
	 * 
	 * Exemplo:
	 * 
	 * @Override
		public Cobject verificarConvencao(String name, TypeOf typeof) {
		return new Cobject("Método contém underscore" ,typeof.equals(TypeOf.METHOD) && name.contains("_"));
		}
	 */
	public Cobject verificarConvencao(String name, TypeOf typeof);
	
	
}
