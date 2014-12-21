package pa.iscde.metrics.extensibility;

public interface DefaultVisitor {

	public int getMethodCounter();

	public int getPhysicalLineCounter();

	public int getStaticMethods();

	public int getInterfaceCounter();

	public int getClassCounter();

	public int getLogicalLineCounter();

	public int getAttributeCounter();
	
}
