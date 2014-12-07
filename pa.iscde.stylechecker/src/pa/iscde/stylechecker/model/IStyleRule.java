package pa.iscde.stylechecker.model;

/**
 * 
 * @author joaomarques
 *
 */
public interface IStyleRule {
	
	/**
	 * 
	 * @param state - true to active the rule or false to deactivate the rule
	 */
	public void setActive(boolean state);
	
	/**
	 * 
	 * @return if the rule is active
	 */
	public boolean getActive();
	
	/**
	 * 
	 * @return the detected rule violations
	 */
	public int getViolations();
	
	/**
	 * Runs the rule verifications
	 */
	public void runStyleRule();
	
	/**
	 * 
	 * @return Rule description 
	 */
	public String getDescription();
	
	

}
