package pa.iscde.conventions.extensability;



public interface ConventionService {
	
	
	/**
	 * Uses the convention that the wishes to use through the class Cobject.
	 * 
	 * @param name - ID(Name) that is to be verified.
	 * @param typeof - Type of Enum defined by TypeOf (METHOD, CLASS, CONSTANTS, ENUM).
	 * @return Cobject - Object of type convention that recives a warning and a convention.
	 * 
	 * 
	 * Any doubts go to : https://code.google.com/p/iscte-pa-2014/wiki/verificarConvencao
	 * 
	 * Exemplo:
	 * 
	 * @Override
		public Cobject verificarConvencao(String name, TypeOf typeof) {
		return new Cobject("M�todo cont�m underscore" ,typeof.equals(TypeOf.METHOD) && name.contains("_"));
		}
	 */
	public Cobject verifyConvention(String name, TypeOf typeof);
	
	
}
