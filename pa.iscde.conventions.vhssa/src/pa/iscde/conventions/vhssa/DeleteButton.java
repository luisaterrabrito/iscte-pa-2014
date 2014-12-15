package pa.iscde.conventions.vhssa;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.dropcode.services.DropButton;
import pa.iscte.dropcode.gui.DropCodeView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * Implementation of the extension point, it deletes all the code of the Fields, Constructors or Methods that the user wants.
 * Note that when you delete one thing , you need to save the file.
 * @author Vítor Sousa
 *
 */

public class DeleteButton implements DropButton{

	Map<String, Image> imageMap;
	Image image;
	
	@Override
	public Image getIcon() {
		imageMap = new HashMap<String, Image>();
		image = imageMap.get("remove.gif");
		if(image != null){
		return image;
		}
		return null;
	}

	@Override
	public String getText() {
		return "Delete";
	}

	@Override
	public void clicked(ASTNode node) {
		Bundle bundle = FrameworkUtil.getBundle(DropCodeView.class);
		BundleContext context  = bundle.getBundleContext();
		JavaEditorServices javaServices;
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
		javaServices.insertText(javaServices.getOpenedFile(), "", node.getStartPosition(), node.getLength());
		
	}

}
