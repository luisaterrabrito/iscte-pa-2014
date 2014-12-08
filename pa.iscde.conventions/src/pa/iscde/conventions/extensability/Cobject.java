package pa.iscde.conventions.extensability;

public class Cobject {
	
	
		String warning;
		boolean condition;
		
		
		public String getWarning() {
			return warning;
		}
		
		public boolean getCondition() {
			return  condition;
		}
		
		
		/**
		 * Convention Object que define o Aviso e a condição a ser verificada.
		 * @param warning - Recebe o aviso desejado para exibir na Classe.
		 * @param condition - Condição que vai ser verificada.
		 */
		public Cobject(String warning, boolean  condition){
			this.warning = warning;
			this. condition =  condition;
		}
		
}
