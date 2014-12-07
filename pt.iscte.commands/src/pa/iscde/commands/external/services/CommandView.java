package pa.iscde.commands.external.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.commands.controllers.ExtensionHandler;
import pa.iscde.commands.models.CommandDefinition;
import pa.iscde.commands.models.CommandWarehouse;
import pa.iscde.commands.models.ViewWarehouse;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pa.iscde.commands.utils.Labels;
import pt.iscte.pidesco.extensibility.PidescoView;

/**
 * @author Fábio Martins
 * 
 * */
final public class CommandView implements PidescoView {

	private CommandTree commandTree;
	private Composite actionsArea;

	public static Composite viewArea;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new GridLayout(1, false));
		createActionButtonsArea(viewArea);

		// This Method can only be called here, because it's when there are
		// already WorkbenchPages and stuff related to the UI
		ViewWarehouse.loadAllViews();

	}

	private void createActionButtonsArea(Composite viewArea) {
		commandTree = new CommandTree(viewArea);
		actionsArea = new Composite(viewArea, SWT.NONE);
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 0;
		fillLayout.spacing = 10;
		actionsArea.setLayout(fillLayout);

		ExtensionHandler handler = new ExtensionHandler();
		handler.setExtensionHandler(ExtensionPointsIDS.ACTION_ID.getID(),
				new ActionHandler(actionsArea, commandTree));
		handler.startProcessExtension();

	}

}
