package pa.iscde.formulas.listeners;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class AddFormulaListener implements SelectionListener{

	private int inputsNumber =0;
	private String [] inputsName;
	private ArrayList<Text> inputs_text = new ArrayList<Text>();

	public AddFormulaListener(){
		createInputsNumberWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL));
	}

	private void createInputsNumberWindow(final Shell dialog) {
		dialog.setText(" Formula Inputs:");
		dialog.setLayout(new GridLayout(2, false));
		Label label_info = new Label(dialog, SWT.NONE);
		label_info.setText("Fill all the fields");
		label_info.setLayoutData(new GridData(GridData.FILL));
		Label lb = new Label(dialog, SWT.NONE);
		lb.setText("");
		lb.setLayoutData(new GridData(GridData.CENTER));
		Label label = new Label(dialog, SWT.NONE);
		label.setText("Number of imputs:");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Text input_text = new Text(dialog, SWT.BORDER);
		input_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) input_text.getLayoutData()).widthHint = 100;

		Button ok = new Button(dialog, SWT.PUSH);
		ok.setText("Continue");
		ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				inputsNumber = Integer.parseInt(input_text.getText());
				createInputsNameWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL), inputsNumber);
				dialog.dispose();
			}
		});

		Button cancel = new Button(dialog, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//				if(formula.name().equals("Areas") || formula.name().equals("Volumes")){
				//					formula.result(null);
				//				}
				dialog.dispose();
			}
		});
		dialog.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				//		    	  if(formula.name().equals("Areas") || formula.name().equals("Volumes")){
				//		    		  formula.result(null);
				//  }
			}
		});

		dialog.setDefaultButton(ok);
		dialog.pack();
		dialog.open();

	}

	private void createInputsNameWindow(final Shell shell, final int inputsNumber) {
		shell.setText("Formula Inputs:");
		shell.setLayout(new GridLayout(2, false));
		Label label_info = new Label(shell, SWT.NONE);
		label_info.setText("Name of the inputs");
		label_info.setLayoutData(new GridData(GridData.FILL));
		Label lb = new Label(shell, SWT.NONE);
		lb.setText("");
		lb.setLayoutData(new GridData(GridData.CENTER));

		for (int i = 0; i < inputsNumber; i++) {
			Label label = new Label(shell, SWT.NONE);
			int j = i+1;
			label.setText("Input " + j +":");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			Text input_text = new Text(shell, SWT.BORDER);
			input_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			((GridData) input_text.getLayoutData()).widthHint = 100;
			inputs_text.add(input_text);
		}
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("Continue");
		ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				inputsName = new String[inputsNumber];
				for (int i = 0; i < inputsName.length; i++) {
					inputsName[i] = inputs_text.get(i).getText();
				}
				createCodeWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL), inputsName);
				shell.dispose();
			}

		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				shell.dispose();
			}
		});
		shell.setDefaultButton(ok);
		shell.pack();
		shell.open();
	}

	private void createCodeWindow(final Shell shell, String[] inputsName) {
		shell.setText("Formula code:");
		shell.setLayout(new GridLayout(1, false));
		Label label_info = new Label(shell, SWT.NONE);
		label_info.setText("Type your formula code");
		label_info.setLayoutData(new GridData(GridData.FILL));
		Label lb = new Label(shell, SWT.NONE);
		lb.setText("");
		lb.setLayoutData(new GridData(GridData.CENTER));
		Text t = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		t.setLayoutData(new GridData(GridData.FILL_BOTH));
		t.setText("//The inputs will be stored in the 'String[]inputs' received in your function. "+ "Inputs: ");
		String text="";
		for (int i = 0; i < inputsName.length; i++) {
			if(i==0){
				text=t.getText();
				text+= "Input[" + i +"]= " + inputsName[i] +",";
			}else if(i!=inputsName.length-1&&i!=0){
				text+= "Input[" + i +"]= " + inputsName[i] +", " ;
			}else{
				text+= "Input[" + i +"]= " + inputsName[i];	
			}
		}
		t.setText(text);
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("Continue");
		ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//criar aqui o ficheiro
				shell.dispose();
			}
		});
		shell.setDefaultButton(ok);
		shell.open();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}



}
