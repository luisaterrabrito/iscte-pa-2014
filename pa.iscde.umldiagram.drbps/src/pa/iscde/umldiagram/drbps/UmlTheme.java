package pa.iscde.umldiagram.drbps;

import org.eclipse.swt.graphics.Color;

public interface UmlTheme {
	/**
	 * enum avaible to set classType
	 */
	public enum ClassType{
		CLASS, ENUM, INTERFACE;
	}
	/**
	 * 
	 * @param className - simple class name
	 * example: class "MyClass.java" , className="MyClass"
	 * className must be available in getter getClassName()
	 * @return swt.color
	 */
	public Color getColor(String className);
	
	/**
	 * 
	 * @return className defined
	 */
	public String getClassName();
	
	/**
	 * 
	 * @param classType must be available in getter getClassType()
	 * example: classType=interface
	 * @return
	 */
	public Color getTypeColor(ClassType classType);
	
	/**
	 * 
	 * @return classType defined
	 */
	public ClassType getClassType();
}
