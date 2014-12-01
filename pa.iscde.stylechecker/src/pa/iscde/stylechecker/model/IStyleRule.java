package pa.iscde.stylechecker.model;

public interface IStyleRule {
	
	public void setActive(boolean state);
	
	public boolean getActive();
	
	//Detect Violations
	public int getViolations();
	
	public void runStyleRule();
	
	public String getDescription();
	
	

}
