package pa.iscde.conventions.pjdcp;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;


/**
 * This implementation use the PackageDiagram extension to modify the colors and
 *  letters of the wanted package given the name of the package
 *  that we want to modify the colors, in this case the chosen package was impl
 * 
 * @author Pedro Cananão
 *
 */


public class ChangePackageColor implements PackageDiagramColorExtension {


	String name = "impl";
	
	@Override
	public Color changeColorLetter(String packageName) {
		if(packageName.contains(name)){
			return ColorConstants.blue;
		}else{
			return ColorConstants.red;
		}
	}

	@Override
	public Color changeColorBackground(String packageName) {
		if(packageName.contains(name)){
			return ColorConstants.yellow;
		}else{
			return ColorConstants.orange;
		}
	}
}
