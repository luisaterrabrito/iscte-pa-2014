package contextdefinitiontests;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;

public class formOutsideClass implements ContextDefinitionInterface {

	@Override
	public String getTargetSnippet() {
		return "foRM";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return new ValidateMessage("This snippet must be out of the class body.", e.isOutsideTopClass());
	}

	@Override
	public String getIdentifier() {
		return "formOutsideClass";
	}

}
