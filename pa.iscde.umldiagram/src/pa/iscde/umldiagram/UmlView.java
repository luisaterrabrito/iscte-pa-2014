package pa.iscde.umldiagram;


import java.awt.Font;
import java.util.Collection;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * @author Nuno e Diogo
 */
public class UmlView implements PidescoView {
	private static UmlView umlView;
	private Graph umlGraph;
	private ProjectBrowserServices browserServices;
	
	public UmlView() {
		umlView = this;
	}
	
	

	@Override
	public void createContents(Composite umlArea,
			Map<String, Image> imageMap) {
		Image img = imageMap.get("background.jpg");
		umlArea.setBackgroundImage(img);
		Label l = new Label(umlArea, SWT.NONE);
		l.setText("Diagrama UML: ");
		umlGraph = new Graph(umlArea, SWT.NONE);
		
	}

	public static UmlView getInstance() {
		return umlView;
	}

	public void paintUml(Collection<SourceElement> currentPackage) {
		PackageElement root = browserServices.getRootPackage();
		for(SourceElement e : root.getChildren()){
			if(e.)
		}
		GraphNode node = new GraphNode(umlGraph, SWT.NONE);
		node.setText(e.getName());
		
	}



	public void setBrowserServices(ProjectBrowserServices browserServices) {
		this.browserServices=browserServices;
		
	}

}
