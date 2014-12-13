package pa.iscde.snippets.external;

public interface ContextDefinitionInterface {

	/**
	 * This method should return a string that identifies and very briefly
	 * describes this context. However it is not mandatory (it can be an empty
	 * String), and it doesn't have to be unique among all the context
	 * definitions.
	 * 
	 * @return a string that very briefly describes this context
	 */
	public String getIdentifier();

	/**
	 * This method has to return the name of the snippet whose context will be
	 * defined. The name is not case sensitive.
	 * 
	 * @return a string that identifies the snippet that the context will apply
	 *         to
	 */
	public String getTargetSnippet();

	/**
	 * This method receives a cursor Context, which is a data object that
	 * contains a lot of information about the context of the cursor. This will
	 * be the method that defines the context. To do so, the method will use the
	 * booleans and Strings present in the context argument, combining them to
	 * to create a condition that will validate the context. The method will
	 * then create a validate message that will contain a boolean representing
	 * that validation result and a message to present to the user in case of a
	 * invalid context.
	 * 
	 * @param a
	 *            data object that contains a lot of information about the
	 *            context of the cursor, such has if it's inside a method, if
	 *            the block where it is is static, final, if it's inside a
	 *            tryblock, etc.
	 * @return ValidateMessage. An object that defines an answer if the context
	 *         is valid or not. It is best described in it's own javadoc.
	 */
	public ValidateMessage validateContext(CursorContext e);

	/**
	 * This class represents the message that the defined context returns to
	 * validate the context or not. The message is composed of a boolean
	 * isValid, that represents if the context is valid. And the error message
	 * is the message that will be shown to the user in case the context is
	 * invalid.
	 * 
	 * @author Rui Madeira
	 *
	 */
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
