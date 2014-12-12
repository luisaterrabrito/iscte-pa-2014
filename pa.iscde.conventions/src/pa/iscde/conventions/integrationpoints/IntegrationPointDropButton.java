package pa.iscde.conventions.integrationpoints;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import pa.iscde.conventions.ConventionsView;
import pa.iscde.dropcode.services.DropButton;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * Implementation of the extension point, it deletes all the code of the Fields, Constructors or Methods that the user wants.
 * Note that when you delete one thing , you need to save the file.
 * @author Vítor Sousa
 *
 */

public class IntegrationPointDropButton implements DropButton{

	
	@Override
	public Image getIcon() {
		Display display = new Display(); //perguntar.s
		Image l = new Image(display, "/pa.iscde.conventions/images/remove.gif");
		return l;
	}

	@Override
	public String getText() {
		return "Delete";
	}

	@Override
	public void clicked(ASTNode node) {
		
		Bundle bundle = FrameworkUtil.getBundle(ConventionsView.class);
		BundleContext context  = bundle.getBundleContext();
		JavaEditorServices javaServices;
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
		
//		javaServices.selectText(javaServices.getOpenedFile(), node.getStartPosition(), node.getLength());
		javaServices.insertText(javaServices.getOpenedFile(), "", node.getStartPosition(), node.getLength());
		
	}

}
