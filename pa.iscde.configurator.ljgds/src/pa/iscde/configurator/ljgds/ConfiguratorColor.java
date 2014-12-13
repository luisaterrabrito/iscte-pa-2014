package pa.iscde.configurator.ljgds;

import java.awt.DisplayMode;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;

public class ConfiguratorColor implements PackageDiagramColorExtension {

	@Override
	public Color changeColorLetter(String packageName) {
		// TODO Auto-generated method stub
		Display device = Display.getCurrent();
		if(packageName.equals("pa.iscde.configurator")){
			return new Color(device,255,0,0);
		}
		else{
			return new Color(device, 0,0,255);
		}
	}

	@Override
	public Color changeColorBackground(String packageName) {
		// TODO Auto-generated method stub
		Display device = Display.getCurrent();
		if(packageName.equals("pa.iscde.configurator")){
			return new Color(device,0,255,0);
		}
		else{
			return new Color(device, 255,0,0);
		}
	}

}
