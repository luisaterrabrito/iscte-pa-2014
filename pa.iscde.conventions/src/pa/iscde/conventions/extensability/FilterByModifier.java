package pa.iscde.conventions.extensability;


public interface FilterByModifier {

	/**	Uses the class Modifier from java to filter the methods that the user wants to check
	 * 	for that is required the use Modifier java because it is with this class that we can filter through method that are
	 * 	private, public, protected, synchronized,etc.
	 *
	 * @param modifier- represents the modifier of the method that the user chooses
	 * 
	 * 
	 * Example
	 * public class testeMetodo implements FilterByModifier{

		@Override
		public int verificarModificadorMetodo() {
		return Modifier.PRIVATE;
			}
		}
	 * 
	 */


	public int verificarModificadorMetodo();



}
