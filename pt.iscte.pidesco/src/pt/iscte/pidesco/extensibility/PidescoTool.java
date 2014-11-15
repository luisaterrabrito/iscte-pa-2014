package pt.iscte.pidesco.extensibility;

/**
 * Represents a Pidesco tool that can be attached to a view. 
 * - A concrete tool may optionally consider to be in active/inactive mode (toggle button).
 * - There must exist a zero-argument constructor
 * - Objects should be stateless, i.e. should have no instance attributes
 */
public interface PidescoTool {

	/**
	 * Runs the tool. Parameter may be ignored if the tool executes always in the same way.
	 * @param activate true if the tool was turned into active mode (toggle button selected), false otherwise.
	 */
	void run(boolean activate);	
}
