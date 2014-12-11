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
		 * Convention Object defines the warning and the condition to verify.
		 * @param warning - It receives the desired warning to show on the class.
		 * @param condition - Condition that is to be verified.
		 */
		public Cobject(String warning, boolean  condition){
			this.warning = warning;
			this. condition =  condition;
		}
		
}
