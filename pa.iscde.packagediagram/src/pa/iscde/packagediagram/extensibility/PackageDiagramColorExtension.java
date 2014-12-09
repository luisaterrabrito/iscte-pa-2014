package pa.iscde.packagediagram.extensibility;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;

public interface PackageDiagramColorExtension {
	
	/**
	 * Change color letter depending on the package name 
	 * @param packageName Package name to change
	 * @return color Selected color 
	 * 
	 * @see <b>Example:</b>
	 * 
	 * <pre>public Color changeColorLetter(String packageName) {</pre>
	 *	<pre><pre>if (packageName.contains("pidesco"))</pre></pre>
	 *		<pre><pre><pre>return ColorConstants.blue;</pre></pre></pre>
	 *	<pre>else</pre>
	 *		<pre><pre><pre>return ColorConstants.red;</pre></pre></pre>
	 *} 		
	 */
	
	Color changeColorLetter(String packageName);
	
	
	/**
	 * Change color background depending on the package name 
	 * @param packageName Package name to change
	 * @return color Selected color 
	 */
	
	
	/**
	 * Change color background depending on the package name 
	 * @param packageName Package name to change
	 * @return color Selected color 
	 * 
	 * @see <b>Example:</b>
	 * 
	 * <pre>public Color changeColorLetter(String packageName) {</pre>
	 *	<pre><pre>if (packageName.contains("pidesco"))</pre></pre>
	 *		<pre><pre><pre>return ColorConstants.white;</pre></pre></pre>
	 *	<pre>else</pre>
	 *		<pre><pre><pre>return ColorConstants.black;</pre></pre></pre>
	 *} 		
	 */
	Color changeColorBackground (String packageName);
	
	/**
	 * add cor fundo, cor letras - especie de esquema de cores, devolve cor
	 * 
	 * 
	 * Próxima extensão: Tecla do lado direito em cima da package, popup com várias coisas que podem fazer
	 */
}
