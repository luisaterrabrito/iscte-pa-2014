# Introduction #

The **pa.iscde.indent** bundle, allow users to easily indent the current file code. This project only allow to indent java or javascript code, all other languages can be implemented using a custom extension point.

![http://i.imgur.com/XvIr7ux.png](http://i.imgur.com/XvIr7ux.png)

# Extension point #

```

/**
 * This interface represent the code indent extensibility protocol.
 * @author t-saribe
 *
 */
public interface ICodeIndenter {
	
	/**
	 * Get the indented string correspondent to file that is opened (on top) in the editor.
	 * @param file (non-null), the current opened file.
	 * @param Object with indent options [indent size, max repeated break Lines, wrap line size, braces position]
	 * @return Code indented as a string.
	 */
	public String indent(File file, CodeIndentOptions options);
	
	/**
	 * Check if the file extension string provided is supported.
	 * @param fileExtension as a string
	 * @return this should return a boolean indicating if this current file extension is supported.
	 */
	public boolean suportFormat(String fileExtension);
}
```
# Listeners #

#>:
  * onIndent
  * onOptionsChange

_More details ASAP_

# Services #

_More details ASAP_

# Details #

_More details ASAP_