package pa.iscde.dropcode.dropreflection;

public enum DropModifier {
	PUBLIC(0), PROTECTED(0), PRIVATE(0), PACKAGE_PRIVATE(0), //
	FINAL(1), STATIC(2), ABSTRACT(3);
	// SYNCHRONIZED(3), TRANSIENT(5), VOLATILE(7), NATIVE(6);

	int id;

	private DropModifier(int id) {
		this.id = id;
	}

	public static DropModifier[] getVisibilityModifiers() {
		return new DropModifier[] { PUBLIC, PROTECTED, PRIVATE };
	}
}
