package pa.iscde.dropcode.ajlfs;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;
import pa.iscde.snippets.external.ContextDefinitionInterface.ValidateMessage;

public class ConditionalExpression implements ContextDefinitionInterface {


	@Override
	public String getIdentifier() {
		return "Conditional Expression";
	}

	@Override
	public String getTargetSnippet() {
		return "type == null ? \"Object\" : type";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {

		if(e.isAbstract()){
			return new ValidateMessage("Abstract", true);
		}else{
			return new ValidateMessage("Not Abstract", false);
		}
	}

}
