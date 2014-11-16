package pa.iscde.filtersearch;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pt.iscte.pidesco.extensibility.PidescoView;

public class SearchView implements PidescoView {

	private static Composite viewArea;

	public SearchView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		SearchView.viewArea = viewArea;
		final Shell shell = new Shell();

		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.verticalSpacing = 8;

		viewArea.setLayout(gridLayout);

		// Search
		Label label = new Label(viewArea, SWT.NULL);
		label.setText("Search: ");

		Text searchText = new Text(viewArea, SWT.SINGLE | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		searchText.setLayoutData(gridData);


		// Filter
		label = new Label(viewArea, SWT.NULL);
		label.setText("Filter");

		Combo filter = new Combo(viewArea, SWT.READ_ONLY);
		filter.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		filter.add("Variables");
		filter.add("Methods");
		filter.add("Classes");
		filter.add("Packages");
		filter.add("All");


		// Results

		label = new Label(viewArea, SWT.NULL);
		label.setText("Results:");

		Text results = new Text(viewArea,SWT.WRAP| SWT.MULTI| SWT.BORDER| SWT.H_SCROLL| SWT.V_SCROLL);
		gridData =  new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.horizontalSpan = 3;
		gridData.grabExcessVerticalSpace = true;

		results.setLayoutData(gridData);

		// Search Button
		final Button searchButton = new Button(viewArea, SWT.PUSH);
		searchButton.setText("Enter");

		gridData = new GridData();
		gridData.horizontalSpan = 4;
		gridData.horizontalAlignment = GridData.END;
		searchButton.setLayoutData(gridData);

		Listener listener = new Listener(){
			@Override
			public void handleEvent(Event event) {
				if(event.widget == searchButton){
					MessageBox dialog =  new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
					dialog.setText("SEARCH CONFIRMATION");
					dialog.setMessage("Do you really want to do this?");

					// open dialog and await user selection
					dialog.open(); 
				}
			}
		};

		searchButton.addListener(SWT.Selection, listener);

	}

}
