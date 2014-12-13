package pa.iscde.packagediagram.internal;

import pt.iscte.pidesco.extensibility.PidescoTool;

/**
 * Colors tool
 *
 */

public class ColorToolPackageDiagram implements PidescoTool {

	@Override
	public void run(boolean activate) {
		 
		 PackageDiagramView.getInstance().loadColorMenu();
		
	}

}
