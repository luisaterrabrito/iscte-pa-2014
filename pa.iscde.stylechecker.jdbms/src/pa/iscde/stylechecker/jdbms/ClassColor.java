package pa.iscde.stylechecker.jdbms;


import org.eclipse.swt.graphics.Color;

import pa.iscde.umldiagram.UmlTheme;

/**
 * Gray theme for uml diagram viewer 
 * @author João Marques a.k.a jdbms @ ISCTE
 *
 */
public class ClassColor implements UmlTheme {

	public ClassColor() {
	}

	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public ClassType getClassType() {
		return ClassType.ALL;
	}

	@Override
	/**
	 * @ return  gray color for all classes 
	 */
	public Color getColor(String className, ClassType type) {
		if(type.equals(getClassType()) )
			return new Color(null, 160, 160, 160);
		return null;
	}

}
