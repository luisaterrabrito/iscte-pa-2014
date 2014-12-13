package pa.iscde.packagediagram.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.packagediagram.extensibility.PackageDiagramActionExtension;
import pa.iscde.packagediagram.extensibility.PackageDiagramColorExtension;
import pa.iscde.packagediagram.model.NodeModelContent;
import pa.iscde.packagediagram.provider.ContentProvider;
import pa.iscde.packagediagram.provider.MyLabelProvider;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * 
 * Implements pidescoView
 *
 */

public class PackageDiagramView implements PidescoView {

	private GraphViewer graph;

	private static final String EXT_POINT_COLOR = "pa.iscde.packagediagram.colorSelector";
	private static final String EXT_POINT_ACTION = "pa.iscde.packagediagram.actionSelector";

	private static PackageDiagramView instance;

	public static PackageDiagramView getInstance() {
		return instance;
	}

	private ProjectBrowserServices browserServices;

	private Composite viewArea;

	public PackageDiagramView() {

		Bundle bundle = FrameworkUtil.getBundle(PackageDiagramView.class);
		BundleContext context = bundle.getBundleContext();

		ServiceReference<ProjectBrowserServices> ref = context
				.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref);

		loadChangeColor();
		loadActionColor();
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		this.viewArea = viewArea;
		graph = new GraphViewer(viewArea, SWT.NONE);

		// Create a root figure and simple layout to contain
		// all other figures

		PackageElement root = browserServices.getRootPackage();

		// vai determinar o que vai ser mostrado para cada nó
		graph.setLabelProvider(new MyLabelProvider(new ChangeColor(
				new PackageDiagramColorExtensionImpl())));
		
		// vai lidar com o conteúdo
		graph.setContentProvider(new ContentProvider());

		NodeModelContent model = new NodeModelContent(root);
		graph.setInput(model.getNodes());

		LayoutAlgorithm layout = setLayout();
		graph.setLayoutAlgorithm(layout, true);
		graph.applyLayout();

	}

	private LayoutAlgorithm setLayout() {
		LayoutAlgorithm layout;
		layout = new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);

		return layout;

	}

	/**
	 * Refresh the new colors from tool
	 * @param key
	 */
	public void refreshColors(String key) {

		if (colorsMap.containsKey(key)) {
			ChangeColor changeColor = colorsMap.get(key);
			graph.setLabelProvider(new MyLabelProvider(changeColor));
		}

	}

	/**
	 * Refresh the new actions from right click mouse
	 * 
	 * @param key
	 * @param packageName
	 */
	public void refreshActions(String key, String packageName) {

		if (actionsMap.containsKey(key)) {
			ChangeAction changeAction = actionsMap.get(key);
			changeAction.run(packageName);
		}

	}

	/**
	 * Create MenuItem and load values - Refresh Colors
	 */
	public void loadColorMenu() {

		Menu menu = new Menu(viewArea.getShell(), SWT.POP_UP);
		String s;

		MenuItem option;
		for (Entry<String, ChangeColor> entry : colorsMap.entrySet()) {
			option = new MenuItem(menu, SWT.NONE);
			s = entry.getKey();
			option.setText(s);
			option.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					MenuItem menuItem = (MenuItem) e.getSource();

					PackageDiagramView.getInstance().refreshColors(
							menuItem.getText());

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		menu.setVisible(true);

	}

	/**
	 * Create MenuItem and load values - Refresh Actions
	 * 
	 * @param packageName
	 */
	
	public void loadActionMenu(final String packageName) {


		Menu menu = new Menu(viewArea.getShell(), SWT.POP_UP);
		String s;
		MenuItem option;
		for (Entry<String, ChangeAction> entry : actionsMap.entrySet()) {
			option = new MenuItem(menu, SWT.NONE);
			s = entry.getKey();
			option.setText(s);
			option.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					MenuItem menuItem = (MenuItem) e.getSource();

					PackageDiagramView.getInstance().refreshActions(
							menuItem.getText(), packageName);

				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		menu.setVisible(true);
	}

	private Map<String, ChangeColor> colorsMap = new HashMap<String, ChangeColor>();
	private Map<String, ChangeAction> actionsMap = new HashMap<String, ChangeAction>();

	/**
	 * Loads all color extensions
	 */
	public void loadChangeColor() {

		String label;

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for (IExtension ext : reg.getExtensionPoint(EXT_POINT_COLOR)
				.getExtensions()) {

			PackageDiagramColorExtension pcce = null;
			try {
				pcce = (PackageDiagramColorExtension) ext
						.getConfigurationElements()[0]
						.createExecutableExtension("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pcce != null) {
				for (IConfigurationElement extension : ext
						.getConfigurationElements()) {
					label = ext.getNamespaceIdentifier() + " : "
							+ extension.getAttribute("Name");

					colorsMap.put(label, new ChangeColor(pcce));
				}
			}
		}
	}

	/**
	 * Loads all action extensions
	 */
	public void loadActionColor() {

		String label;

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for (IExtension ext : reg.getExtensionPoint(EXT_POINT_ACTION)
				.getExtensions()) {

			PackageDiagramActionExtension pcae = null;
			try {
				pcae = (PackageDiagramActionExtension) ext
						.getConfigurationElements()[0]
						.createExecutableExtension("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pcae != null) {
				for (IConfigurationElement extension : ext
						.getConfigurationElements()) {
					label = ext.getNamespaceIdentifier() + " : "
							+ extension.getAttribute("Name");

					actionsMap.put(label, new ChangeAction(pcae));
				}
			}
		}
	}

	/**
	 * Choose the action
	 */
	public static class ChangeAction {
		private PackageDiagramActionExtension tempPcae;

		public ChangeAction(PackageDiagramActionExtension pcae) {
			tempPcae = pcae;
		}

		public void run(String packageName) {
			tempPcae.run(packageName);
		}

	}

	/**
	 * Choose the color
	 */
	public static class ChangeColor {

		private PackageDiagramColorExtension tempPcce;

		public ChangeColor(PackageDiagramColorExtension pcce) {
			tempPcce = pcce;
		}

		public Color getForeground(String packageName) {
			return tempPcce.changeColorLetter(packageName);
		}

		public Color getBackground(String packageName) {
			return tempPcce.changeColorBackground(packageName);
		}

	};

}
