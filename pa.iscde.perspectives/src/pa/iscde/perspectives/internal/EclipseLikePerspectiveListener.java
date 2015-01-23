package pa.iscde.perspectives.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import pa.iscde.perspectives.extensibility.PerspectiveListener;
import pa.iscde.perspectives.internal.ui.PerspectiveSwitcherView;

public class EclipseLikePerspectiveListener implements PerspectiveListener
{

	@Override
	public void onPerspectiveActivation()
	{
		MessageBox messageBox = new MessageBox(PerspectiveSwitcherView.getInstance().getShell(), SWT.ICON_INFORMATION);
		messageBox.setText("Activating EclipseLike Perspective");
		messageBox.setMessage("Warming up engines!");
		messageBox.open();
	}

	@Override
	public void afterPerspectiveActivation()
	{
		MessageBox messageBox = new MessageBox(PerspectiveSwitcherView.getInstance().getShell(), SWT.ICON_INFORMATION);
		messageBox.setText("Activated EclipseLike Perspective");
		messageBox.setMessage("Whoa! Nice perspective!");
		messageBox.open();
	}

	@Override
	public void onPerspectiveDeactivation()
	{
		MessageBox messageBox = new MessageBox(PerspectiveSwitcherView.getInstance().getShell(), SWT.ICON_INFORMATION);
		messageBox.setText("Deactivating EclipseLike Perspective");
		messageBox.setMessage("Sad to see you go :(");
		messageBox.open();
	}

	@Override
	public void afterPerspectiveDeactivation()
	{
		MessageBox messageBox = new MessageBox(PerspectiveSwitcherView.getInstance().getShell(), SWT.ICON_INFORMATION);
		messageBox.setText("Deactivated EclipseLike Perspective");
		messageBox.setMessage("niff sniff");
		messageBox.open();
	}

}
