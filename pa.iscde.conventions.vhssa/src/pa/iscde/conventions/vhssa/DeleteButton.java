package pa.iscde.conventions.vhssa;


import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
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

	Image image;
	
	@Override
	public Image getIcon() {
		image = new Image(Display.getCurrent(), "C:/Users/user/Desktop/ISCTE/Mestrado/Segundo Ano/PA/pa.iscde.conventions.vhssa/images/remove.gif");
		if(image != null){
		return image;
		}else{
			return null;
		}
	}

	@Override
	public String getText() {
		if(image == null){
		return "Delete";
		}else{
			return "";
		}
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
