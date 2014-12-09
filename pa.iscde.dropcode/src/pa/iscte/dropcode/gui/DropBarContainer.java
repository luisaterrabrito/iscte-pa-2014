package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import pa.iscde.dropcode.services.DropBar;

public class DropBarContainer extends Composite {

	private DropBar dropBar;

	public DropBarContainer(Composite parent, DropBar dropBar) {
		super(parent, SWT.NONE);
		this.dropBar = dropBar;
		setLayout(new FillLayout());
		setText(dropBar.getName());
		// dropBar.createContents(this);
	}

	public void setText(String string) {
		// TODO
	}

	public void clear() {
		for (Control child : getChildren()) {
			// System.out.println("Disposing of " + child);
			child.dispose();
		}
	}

	public DropBar getDropBar() {
		return dropBar;
	}

	public void updateContents() {
		clear();
		dropBar.createContents(this);
		setSize(computeSize(SWT.DEFAULT, SWT.DEFAULT));
		layout(true, true); // TODO
	}
}
