package pa.iscde.snippets.gui;

public class CommandExtensionHelper {

	public static void savedSnippetCommand(){
		if(SnippetsView.getInstance().getSnippetCode() != null)
			SnippetsView.getInstance().getSnippetCode().saveButtonFunction();
	}
	
	public static void discardSnippetCommand(){
		if(SnippetsView.getInstance().getSnippetCode() != null)
			SnippetsView.getInstance().getSnippetCode().discardButtonFunction();
	}

	public static void useCommandSnippet() {
		if(SnippetsView.getInstance().getSnippetCode() != null)
			SnippetsView.getInstance().getSnippetCode().useButtonFunction();
	}

	public static void setFilterToLanguage(String language) {
		if(SnippetsView.getInstance().getSnippetCode() != null)
			SnippetsView.getInstance().getSnippetCode().setLanguage(language);
		else if(SnippetsExplorer.getInstance().isVisible()){
			SnippetsExplorer.getInstance().setLanguage(language);
		}
	}

	public static void createNewSnippetCommand(String selectedText) {
		if(SnippetsExplorer.getInstance().isVisible()){
		SnippetsExplorer.getInstance().hideUnhide();
		SnippetsView.getInstance().createSnippetCode(selectedText);
		}
	}
}
