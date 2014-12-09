package pa.iscte.dropcode.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import pa.iscde.dropcode.services.DropBar;

public class DropBarContainer extends Composite {

	private DropBar dropBar;
	private Composite header, content;
	private Label title;

	public DropBarContainer(Composite parent, DropBar dropBar) {
		super(parent, SWT.BORDER);
		this.dropBar = dropBar;

		GridLayout gridLayout = new GridLayout();
		setLayout(gridLayout);

		GridData headerData = new GridData();
		headerData.grabExcessVerticalSpace = false;
		headerData.minimumHeight = 36;
		header = new Composite(this, SWT.NONE);
		header.setLayoutData(headerData);

		title = new Label(header, SWT.NONE);
		title.setText(dropBar.getName());

		GridData contentData = new GridData();
		contentData.grabExcessVerticalSpace = true;
		content = new Composite(this, SWT.NONE);
		content.setLayoutData(contentData);
	}

	public void clearContents() {
		for (Control child : content.getChildren()) {
			// System.out.println("Disposing of " + child);
			child.dispose();
		}
	}

	public DropBar getDropBar() {
		return dropBar;
	}

	public void updateContents() {
		clearContents();
		dropBar.createContents(content);
		title.setText(dropBar.getName());
		content.setSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		setSize(computeSize(SWT.DEFAULT, SWT.DEFAULT));
		layout(true, true); // TODO
	}
}
