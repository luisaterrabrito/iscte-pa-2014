package pa.iscde.formulas.util;

public class JavaToLatexFormat {
	
	private String javaOperation;
	private String operationLatexFormat;
	
	public JavaToLatexFormat(String javaOperation, String operationLatexForma) {
		super();
		this.javaOperation = javaOperation;
		this.operationLatexFormat = operationLatexForma;
	}

	public String getJavaOperation() {
		return javaOperation;
	}

	public void setJavaOperation(String javaOperation) {
		this.javaOperation = javaOperation;
	}

	public String getOperationLatexFormat() {
		return operationLatexFormat;
	}

	public void setOperationLatexFormat(String operationLatexFormat) {
		this.operationLatexFormat = operationLatexFormat;
	}
	
	
	
	

}
