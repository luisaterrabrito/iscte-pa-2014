package pa.iscde.conventions.integrationpoints;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;
import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;


/**
 * This implementation use the PackageDiagram extension to modify the colors and
 *  letters of the wanted package, in this case the chosen package was impl
 * 
 * @author Pedro Cananão
 *
 */



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
