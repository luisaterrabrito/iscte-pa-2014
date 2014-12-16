package pa.iscde.umldiagram.drbps;

import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphNode;

import pa.iscde.packagediagram.extensibility.PackageDiagramActionExtension;

/**
 * this class implements the interface PackageDiagramActionExtension from PackageDiagram
 * @author DiogoPeres e Nuno
 *
 */
public class UmlActionSelection implements PackageDiagramActionExtension {

	public UmlActionSelection() {
	}
	
	

	@Override
	public void run(String packageName) {
		GraphNode g = new GraphNode(UmlView.getInstance().getUmlGraph(), SWT.NONE);
		g.setText("Package "+packageName+"");

	}

}
