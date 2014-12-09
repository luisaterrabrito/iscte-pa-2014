package pa.iscde.formulas.extensibility;

/**
 * Interface used by the extension point AddFormula.
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public interface CreateFormulaProvider {
	
	public String setName();

	public String[] setInputs();

	public String setMethodCode();

	public String setCategory();
	
	public String setResult();

}
