package pa.iscde.stylechecker.model;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * 
 * @author joaomarques
 *
 */
public interface IStyleRule {

	public void setActive(boolean state);
	
	/**
	 * 
	 * @return if the rule is active
	 */
	public boolean getActive();
	
	/**
	 * 
	 * @return number of detected rule violations
	 */
	public int getViolations();
	
	
	/**
	 * 
	 * @return Rule description 
	 */
	public String getDescription();
	
	
	public  boolean check(ASTNode node) ;
		
	
	public  String getWarningMessage() ;

}
