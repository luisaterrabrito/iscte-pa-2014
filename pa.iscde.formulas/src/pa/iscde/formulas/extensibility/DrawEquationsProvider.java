package pa.iscde.formulas.extensibility;

/**
 * Interface used by the extension point.
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public interface DrawEquationsProvider {
	
	public String setJavaOperation();
	
	public String setOperationLatexFormat();

}
