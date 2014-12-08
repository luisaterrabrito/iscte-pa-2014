package pa.iscde.commands.external.services;


import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import pa.iscde.commands.controllers.ExtensionHandler;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pt.iscte.pidesco.extensibility.PidescoView;

/**
 * @author Fábio Martins
 * 
 * */
final public class CommandView implements PidescoView {

	private CommandViewTree commandTree;
	private Composite actionsArea;

	public static Composite viewArea;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new GridLayout(1, false));
		createActionButtonsArea(viewArea);

	}

	private void createActionButtonsArea(Composite viewArea) {
		commandTree = new CommandViewTree(viewArea);
		actionsArea = new Composite(viewArea, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 0;
		fillLayout.spacing = 10;
		actionsArea.setLayout(fillLayout);

		ExtensionHandler handler = ExtensionHandler.getInstance();
		handler.setExtensionHandler(ExtensionPointsIDS.ACTION_ID.getID(),
				new ActionHandler(actionsArea, commandTree));
		handler.startProcessExtension();

	}

}
