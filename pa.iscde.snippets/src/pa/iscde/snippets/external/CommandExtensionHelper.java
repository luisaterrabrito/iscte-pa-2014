package pa.iscde.snippets.external;

import activator.SnippetsActivator;
import pa.iscde.snippets.gui.SnippetsView;

public class CommandExtensionHelper {

	public static void savedSnippetCommand() {
		SnippetsView.getInstance().saveButtonFunction();
	}

	public static void discardSnippetCommand() {
		SnippetsView.getInstance().discardButtonFunction();
	}

	public static void useCommandSnippet() {
		SnippetsView.getInstance().useButtonFunction();
	}

	public static void setFilterToLanguage(String language) {
		SnippetsView.getInstance().setFilterLanguage(language);
	}

	public static void createNewSnippetCommand() {
		SnippetsView.getInstance().createNewSnippetCommand(
				SnippetsActivator.getInstance().getSelectedText());
	}
}
