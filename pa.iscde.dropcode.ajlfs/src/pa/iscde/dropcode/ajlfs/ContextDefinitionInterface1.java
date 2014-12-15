package pa.iscde.dropcode.ajlfs;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;

public class ContextDefinitionInterface1 implements ContextDefinitionInterface {

	@Override
	public String getIdentifier() {
		return "conditional expression";
	}

	@Override
	public String getTargetSnippet() {
		return "condition ? iftrue : iffalse";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return null;
	}

}
