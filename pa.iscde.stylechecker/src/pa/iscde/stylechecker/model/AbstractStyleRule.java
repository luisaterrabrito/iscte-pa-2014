package pa.iscde.stylechecker.model;

import org.eclipse.jdt.core.dom.ASTNode;

public  abstract  class AbstractStyleRule implements IStyleRule {
	
	private boolean state;
	private int numbViolations;
	private String description;
	
	/**
	 * 
	 * @param state - true to active the rule or false to deactivate the rule
	 */
	public void setActive(boolean state) {
		this.state=state;
	}
	
	/**
	 * 
	 * @return if the rule is active
	 */
	public boolean getActive() {
		return this.state;
	}
	
	/**
	 * 
	 * @return number of detected rule violations
	 */
	public int getViolations() {
		return this.numbViolations;
	}
	
	
	/**
	 * 
	 * @return Rule description 
	 */
	public String getDescription() {
		return this.description;
	}
	
	
	public abstract boolean check(ASTNode node) ;
		
	
	public abstract String getWarningMessage() ;

	

}
