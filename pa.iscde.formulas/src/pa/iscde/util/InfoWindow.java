package pa.iscde.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class InfoWindow {
	
	public static void createWindow(String title, String solution) {
		final Shell dialog = new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText(title+ " results:");
		dialog.setLayout(new GridLayout(1, true));
		Label label_info = new Label(dialog, SWT.NONE);
		label_info.setText(solution);
		label_info.setLayoutData(new GridData(GridData.FILL));
		Button ok = new Button(dialog, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				dialog.dispose();
			}
		});
		dialog.pack();
	    dialog.open();
	}

}
