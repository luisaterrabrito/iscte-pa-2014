package pa.iscde.snippets.extensionhandlers;

public class CursorContext {
	private String openedFileExtension;
	private String snippetLanguage;
	private boolean insideMethod = false;
	private boolean insideClass = false;
	
	public CursorContext(String openedFileExtension, String snippetLanguage,
			boolean insideMethod, boolean insideClass) {
		super();
		this.openedFileExtension = openedFileExtension;
		this.snippetLanguage = snippetLanguage;
		this.insideMethod = insideMethod;
		this.insideClass = insideClass;
	}
}
