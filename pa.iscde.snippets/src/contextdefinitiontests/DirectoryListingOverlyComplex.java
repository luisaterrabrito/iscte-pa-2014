package contextdefinitiontests;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;

public class DirectoryListingOverlyComplex implements
		ContextDefinitionInterface {

	@Override
	public String getIdentifier() {
		return "DirectoryListingOverlyComplex";
	}

	@Override
	public String getTargetSnippet() {
		return "DirectoryListing";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		boolean valid = false;
		if (e.isSynchronized() && e.isStatic() && e.isInsideTry()
				&& !e.isInterface() && e.getVisibility().equals("public")
				&& e.getOpenedFileExtension().equals(e.getSnippetLanguage()))
			valid = true;
		return new ValidateMessage(
				"This is too complicated to explain", valid);
	}
}
