package pa.iscde.stylechecker.sipke;

import pa.iscde.stylechecker.model.IStyleRule;

public class DummyRule implements IStyleRule {

	private boolean state;
	private int violations;
	private String description;
	
	public DummyRule(String description, int violations, boolean state) {
		this.description = description;
		this.violations=violations;
		this.state = state;
	}
	

	@Override
	public void setActive(boolean state) {
		this.state = state;

	}

	@Override
	public boolean getActive() {
		return state;
	}

	@Override
	public int getViolations() {
		return violations;
	}

	@Override
	public void runStyleRule() {

	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
