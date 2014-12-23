package pa.iscde.tasklist.jmmpo11;

import org.eclipse.swt.graphics.Color;

import pa.iscde.umldiagram.UmlTheme;

public class TaskListUmlTheme implements UmlTheme{
	
	@Override
	public String getClassName() {
		return null;
	}

	@Override
	public ClassType getClassType() {
		return ClassType.CLASS;
	}

	@Override
	public Color getColor(String className, ClassType type){
		return new Color(null, 173, 216, 230);
	}
}
