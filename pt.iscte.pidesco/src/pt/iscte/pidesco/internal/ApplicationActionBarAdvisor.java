package pt.iscte.pidesco.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import pt.iscte.pidesco.extensibility.PidescoExtensionPoint;
import pt.iscte.pidesco.internal.PidescoActivator.ViewComponent;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager viewsMenu = new MenuManager("&Views", "views");
		menuBar.add(viewsMenu);
		List<ViewComponent> components = new ArrayList<ViewComponent>(PidescoActivator.getInstance().getComponents());
		Collections.sort(components);

		for(final ViewComponent vc : components)
			viewsMenu.add(new Action() {
				@Override
				public String getText() {
					return vc.viewTitle;
				}

				@Override
				public String getId() {
					return vc.pluginId + "." + vc.viewId;
				}

				@Override
				public ImageDescriptor getImageDescriptor() {
					return vc.icon == null ? super.getImageDescriptor() : vc.icon;
				}
				
				@Override
				public void run() {
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						page.showView(PidescoExtensionPoint.VIEW.getId(), vc.getSecondaryId(), IWorkbenchPage.VIEW_ACTIVATE);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}

			});
	}
}
