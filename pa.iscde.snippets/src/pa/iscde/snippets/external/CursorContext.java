package pa.iscde.snippets.external;

public class CursorContext {
	private String openedFileExtension;
	private String snippetLanguage;
	private boolean outsideTopClass = false;
	private boolean insideMethod = false;
	private boolean insideIf = false;
	private boolean insideTry = false;
	private boolean insideCatch = false;
	private boolean insideFor = false;
	private boolean isAbstract = false;
	private boolean isFinal = false;
	private boolean isStatic = false;
	private boolean isInterface = false;
	private boolean isNative = false;
	private boolean isTransient = false;
	private boolean isSynchronized = false;
	private boolean isVolatile = false;
	private String visibility = "public";

	public CursorContext(String openedFileExtension, String snippetLanguage,
			boolean outsideTopClass, boolean insideMethod, boolean insideIf,
			boolean insideTry, boolean insideCatch, boolean insideFor,
			boolean isAbstract, boolean isFinal, boolean isStatic,
			boolean isInterface, boolean isNative, boolean isTransient,
			boolean isSynchronized, boolean isVolatile, String visibility) {
		this.openedFileExtension = openedFileExtension;
		this.snippetLanguage = snippetLanguage;
		this.outsideTopClass = outsideTopClass;
		this.insideMethod = insideMethod;
		this.insideIf = insideIf;
		this.insideTry = insideTry;
		this.insideCatch = insideCatch;
		this.insideFor = insideFor;
		this.isAbstract = isAbstract;
		this.isFinal = isFinal;
		this.isStatic = isStatic;
		this.isInterface = isInterface;
		this.isNative = isNative;
		this.isTransient = isTransient;
		this.isSynchronized = isSynchronized;
		this.isVolatile = isVolatile;
		this.visibility = visibility;
	}

	public String getOpenedFileExtension() {
		return openedFileExtension;
	}

	public String getSnippetLanguage() {
		return snippetLanguage;
	}

	public boolean isOutsideTopClass() {
		return outsideTopClass;
	}

	public boolean isInsideMethod() {
		return insideMethod;
	}

	public boolean isInsideIf() {
		return insideIf;
	}

	public boolean isInsideTry() {
		return insideTry;
	}

	public boolean isInsideCatch() {
		return insideCatch;
	}

	public boolean isInsideFor() {
		return insideFor;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public boolean isNative() {
		return isNative;
	}

	public boolean isTransient() {
		return isTransient;
	}

	public boolean isSynchronized() {
		return isSynchronized;
	}

	public boolean isVolatile() {
		return isVolatile;
	}

	public String getVisibility() {
		return visibility;
	}
}
