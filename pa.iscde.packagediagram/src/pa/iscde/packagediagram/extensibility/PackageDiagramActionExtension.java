package pa.iscde.packagediagram.extensibility;

public interface PackageDiagramActionExtension {

	/**
	 * Executes an action based in Packages
	 * 
	 * @param packageName
	 * 
	 * @see <b>Example:</b>
	 * 
	 * <pre>public void(String packageName) {</pre>
	 *		<pre><pre>system.out.println(packaName);</pre></pre>
	 *} 		
	 */
	
	public void run(String packageName);

}
