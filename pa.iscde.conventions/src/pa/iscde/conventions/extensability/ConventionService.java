package pa.iscde.conventions.extensability;



public interface ConventionService {
	
	
	/**
	 * Uses the convention that the wishes to use through the class Cobject.
	 * 
	 * @param name - ID(Name) that is to be verified.
	 * @param typeof - Type of Enum defined by TypeOf (METHOD, CLASS, CONSTANTS, ENUM).
	 * @return Cobject - Object of type convention that recives a warning and a convention.
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
