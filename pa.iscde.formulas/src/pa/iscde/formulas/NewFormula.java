package pa.iscde.formulas;

import java.util.Arrays;


/**
 * Generic class of the new formulas created by the user.
 * @author Gonçalo Horta & Tiago Saraiva
 *
 */
public class NewFormula extends Formula{

	private String name;
	private String[] inputs;
	private String algorithm;
	private String javacode;
	
	/**
	 * @param name
	 * @param inputs
	 * @param algorithm
	 * @param javacode
	 */
	public NewFormula(String name, String[] inputs, String algorithm,
			String javacode) {
		this.name = name;
		this.inputs = inputs;
		this.algorithm = algorithm;
		this.javacode = javacode;
	}
	
	
	@Override
	public String[] inputs() {
		return inputs;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String result(String[] inputs) {
		return algorithm;
	}
	
	@Override
	public String methodCode(String file) {
		return javacode;
	}


	@Override
	public String toString() {
		return "NewFormula [name=" + name + ", inputs="
				+ Arrays.toString(inputs) + ", algorithm=" + algorithm
				+ ", javacode=" + javacode + "]";
	}
	
	

}
