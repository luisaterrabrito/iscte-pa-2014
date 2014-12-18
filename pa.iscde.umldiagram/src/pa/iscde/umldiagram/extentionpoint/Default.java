package pa.iscde.umldiagram.extentionpoint;

import org.eclipse.swt.graphics.Color;

import pa.iscde.umldiagram.UmlTheme;

/**
 * Example of the implementation of the interface UmlTheme
 * @author Nuno e Diogo
 *
 */
public class Default implements UmlTheme{

	private String className="";

	

	@Override
	public String getClassName() {
		return className;
	}

	

	@Override
	public ClassType getClassType() {
		return ClassType.ALL;
	}



	@Override
	public Color getColor(String className, ClassType type) {
		Color c = new Color(null, 0, 0, 255);
		return c;
	}


	
	
}
