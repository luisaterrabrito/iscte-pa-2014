package pa.iscde.formulas.util;

public class FormulaAnnotation {
	
	private String formula;
	private int offset;
	private int lenght;
	
	/**
	 * Class to add a Annotation for each formula
	 * 
	 * @param formula
	 * @param offset
	 * @param lenght
	 */
	public FormulaAnnotation(String formula, int offset, int lenght) {
		super();
		this.formula = formula;
		this.offset = offset;
		this.lenght = lenght;
	}
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLenght() {
		return lenght;
	}
	public void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	@Override
	public String toString() {
		return "Formula annotation: "+formula+" Offset:"+offset+" Length:"+lenght;
	}

}
