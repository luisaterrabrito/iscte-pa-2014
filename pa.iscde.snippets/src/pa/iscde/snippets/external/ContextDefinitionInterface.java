package pa.iscde.snippets.external;


public interface ContextDefinitionInterface {

	public String getIdentifier();
	
	public String getTargetSnippet();

	public ValidateMessage validateContext(CursorContext e);

	public class ValidateMessage {
		private String errorMessage;
		private boolean isValid;

		public ValidateMessage(String message, boolean isValid) {
			super();
			this.errorMessage = message;
			this.isValid = isValid;
		}

		public String getMessage() {
			return errorMessage;
		}
		
		public boolean isValid() {
			return isValid;
		}
	}
}
