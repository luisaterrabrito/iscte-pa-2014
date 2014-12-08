package pa.iscde.stylechecker.sipke;

import org.eclipse.jdt.core.dom.ASTNode;

import pa.iscde.stylechecker.model.AbstractStyleRule;

public class DummyRule extends AbstractStyleRule {


	private String description;
	
	public DummyRule(String description, int violations, boolean state) {
		this.description = description;
		super.setActive(state);
		//TODO setDescription
		//TODO setViolations
	}
	

	@Override
	public boolean check(ASTNode node) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getWarningMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
