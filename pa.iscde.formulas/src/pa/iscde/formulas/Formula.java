package pa.iscde.formulas;

public interface Formula {	
	
	public String name();	
	public String methodCode();
	public String[] inputs();
	public String result(String[] inputs);
	
}
