package pa.iscde.stylechecker.sipke;

import org.eclipse.jdt.core.dom.ASTNode;

import pa.iscde.stylechecker.model.AbstractStyleRule;

public class DummyRule extends AbstractStyleRule {


	
	public DummyRule(String description, int violations, boolean state) {
		super.setActive(state);
	 }
	

	@Override
	public boolean check(ASTNode node) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getWarningMessage() {
		return "Dummy Warnig";
	}


	@Override
	public String getDescription() {
		return "Dummy description";
	}

}
