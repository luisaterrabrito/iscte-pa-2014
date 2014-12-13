package pa.iscde.packagediagram.internal;

import org.eclipse.swt.graphics.Color;

import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;

public class PackageDiagramColorExtensionImpl implements PackageDiagramColorExtension{

	@Override
	public Color changeColorLetter(String packageName) {
		return new Color(null, 150, 0, 0);
	}

	@Override
	public Color changeColorBackground(String packageName) {
		return new Color(null, 250, 200, 200);
	}

}
