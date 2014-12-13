package pa.iscde.umldiagram.extentionpoint;

import org.eclipse.swt.graphics.Color;

import pa.iscde.umldiagram.UmlTheme;

public class Default implements UmlTheme{

	private String className="ObjetoJogo";
	
	@Override
	public Color getColor(String className) {
		Color c = new Color(null, 0, 0, 255);
		return c;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public Color getTypeColor(ClassType classType) {
		Color c = new Color(null, 0, 255, 5);
		return c;
	}

	@Override
	public ClassType getClassType() {
		return ClassType.ENUM;
	}


	
	
}
