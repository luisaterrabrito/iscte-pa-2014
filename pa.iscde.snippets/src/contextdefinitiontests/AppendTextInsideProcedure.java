package contextdefinitiontests;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;

public class AppendTextInsideProcedure implements ContextDefinitionInterface {

	@Override
	public String getTargetSnippet() {
		return "appendTextTOFIle";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return new ValidateMessage("This snippet must be inside a procedure or function.", e.isInsideMethod());
	}

}
