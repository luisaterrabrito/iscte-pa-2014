package pa.iscde.snippets.external;

import java.io.File;

import pa.iscde.snippets.gui.SnippetsView;
import activator.SnippetsActivator;

public class SearchProviderExtensionHelper {

	public static void snippetCodeFromSearch(File selectedSnippet) {
		SnippetsActivator.getInstance().openSnippetsView();
		SnippetsView.getInstance().snippetCodeFromSearch(selectedSnippet);
	}
}
