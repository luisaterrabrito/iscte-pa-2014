package pa.iscde.perspectives.internal.ui;

import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import pa.iscde.perspectives.model.Perspective;
import pa.iscde.perspectives.services.PerspectiveServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;

public class PerspectiveSwitcherView implements PidescoView
{
	private static final String				VIEW_ID		= "pt.iscte.pidesco.perspectives.perpectives-switcher";
	private static final String				PLUGIN_ID	= "pa.iscde.perspectives";
	private static PerspectiveSwitcherView	instance;
	private static PidescoServices			pidescoServices;
	private static PerspectiveServices		perspectiveServices;
	private Shell							shell;
	private Image							cross;
	private Table							table;
	private Composite						viewArea;

	public PerspectiveSwitcherView()
	{
		BundleContext context = FrameworkUtil.getBundle(PerspectiveSwitcherView.class).getBundleContext();
		pidescoServices = context.getService(context.getServiceReference(PidescoServices.class));
		perspectiveServices = context.getService(context.getServiceReference(PerspectiveServices.class));
		cross = pidescoServices.getImageFromPlugin(PLUGIN_ID, "cross.gif");
	}
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap)
	{
		// set singleton instance
		instance = this;
		this.shell = viewArea.getShell();
		this.viewArea = viewArea;
		createTable();
	}
	public void showWarning(String title, String message)
	{
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING);
		messageBox.setText(title);
		messageBox.setMessage(message);
		messageBox.open();
	}
	private void createTable()
	{
		// create UI
		table = new Table(viewArea, SWT.FULL_SELECTION);
		// perspectiveCol
		TableColumn perspectiveColumn = new TableColumn(table, SWT.NONE);
		perspectiveColumn.setText("Perspective");
		perspectiveColumn.setAlignment(SWT.CENTER);
		perspectiveColumn.setWidth(100);
		TableColumn sourceColumn = new TableColumn(table, SWT.NONE);
		sourceColumn.setText("Source");
		sourceColumn.setAlignment(SWT.CENTER);
		sourceColumn.setWidth(100);
		TableColumn perspectiveIdColumn = new TableColumn(table, SWT.NONE);
		perspectiveIdColumn.setText("Unique ID");
		perspectiveIdColumn.setAlignment(SWT.CENTER);
		perspectiveIdColumn.setWidth(100);
		perspectiveServices.refreshAvailablePerspectives();
		table.addListener(SWT.MouseDoubleClick, new PerspectiveDoubleClickListener());
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		perspectiveColumn.pack();
		sourceColumn.pack();
		perspectiveIdColumn.pack();
	}
	/**
	 * Get PerspectiveServices instance
	 * 
	 * @return
	 */
	public static PerspectiveServices getPerspectiveServices()
	{
		return perspectiveServices;
	}
	/**
	 * Get PidescoServices instance
	 * 
	 * @return
	 */
	public static PidescoServices getPidescoServices()
	{
		return pidescoServices;
	}
	// For singleton instance purposes
	/**
	 * Get's singleton instance. Contains code bellonging to
	 * /pt.iscte.pidesco.projectbrowser/src/pt/iscte/pidesco/projectbrowser/internal/ProjectBrowserView.java
	 * 
	 * @return
	 */
	public static PerspectiveSwitcherView getInstance()
	{
		// source: /pt.iscte.pidesco.projectbrowser/src/pt/iscte/pidesco/projectbrowser/internal/ProjectBrowserView.java
		if (instance == null)
			pidescoServices.openView(VIEW_ID);

		return instance;
	}
	public void forcePerspectiveListRefresh()
	{
		if(viewArea.isDisposed())
			return;
		table.removeAll();
		for (Perspective p : perspectiveServices.getAvailablePerspectives())
		{
			TableItem tItem = new TableItem(table, SWT.None);
			tItem.setText(new String[] { p.getName(), p.getSourcePlugin(), p.getId() });
			if (p.isValid())
			{
				tItem.setImage(p.getIcon());
				if (perspectiveServices.isCurrentPerspective(p))
					tItem.setBackground(new Color(Display.getCurrent(), 125, 255, 125));
			}
			else
			{
				tItem.setImage(cross);
				tItem.setGrayed(true);
				tItem.setBackground(new Color(Display.getCurrent(), 255, 125, 125));
			}
			tItem.setData(p);
		}
	}
	public Shell getShell()
	{
		return shell;
	}
}
