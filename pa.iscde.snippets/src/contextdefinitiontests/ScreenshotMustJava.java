package contextdefinitiontests;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;

public class ScreenshotMustJava implements ContextDefinitionInterface {

	@Override
	public String getTargetSnippet() {
		return "Screenshot";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return new ValidateMessage("This snippet must be in a java file.", e
				.getOpenedFileExtension().toLowerCase()
				.equals(e.getSnippetLanguage().toLowerCase()));
	}

}
