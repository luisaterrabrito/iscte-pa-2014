package pa.iscde.conventions.integrationpoints;



import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;


import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;

public class IntegrationPointPackageDiagram implements PackageDiagramColorExtension {

	@Override
	public Color changeColorLetter(String packageName) {
		if(packageName.contains("impl")){
			return ColorConstants.blue;
		}else{
			return ColorConstants.red;
		}
	}

	@Override
	public Color changeColorBackground(String packageName) {
		if(packageName.contains("impl")){
			return ColorConstants.yellow;
		}else{
			return ColorConstants.orange;
		}
	}

}
