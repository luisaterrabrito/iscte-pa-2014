package pa.iscde.snippets.external;

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

	public static void createNewSnippetCommand(String selectedText) {
		SnippetsView.getInstance().createNewSnippetCommand(selectedText);
	}
}
