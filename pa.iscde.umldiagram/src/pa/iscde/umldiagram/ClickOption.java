
package pa.iscde.umldiagram;

import org.eclipse.swt.events.MouseListener;
import org.eclipse.zest.core.widgets.GraphNode;







/**
 * This extension point offers other plugins the ability to define the action performed when clicking a class in the diagram.

   Requirements: a class that implements our interface

 * @author Nuno & Diogo
 *
 */
public interface ClickOption {
	/**
	 * choose what happens in double click option to each graph node.
	 * Example of a node name: "<interface> MyInterface".
	 * Example of a node name: "MyClass".
	 * Example of a node name: "<enum> MyEnum".
	 * @return org.eclipse.swt.events.MouseListener()
	 */
	public void getAction(GraphNode nodeClicked);
	
	
}
