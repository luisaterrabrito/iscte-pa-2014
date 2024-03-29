package pa.iscde.formulas.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pa.iscde.formulas.Formula;
import pa.iscde.formulas.util.InfoWindow;

/**
 * Class that implements SelectionListener used to draw and execute the calculator mode.
 * @author Gon�alo Horta & Tiago Saraiva
 *
 */
public class CalculatorListener implements SelectionListener{
		
		private ArrayList<Text> inputs_text = new ArrayList<Text>();
		private Formula formula;
		
		public CalculatorListener(Formula formula) {
			this.formula=formula;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			inputs_text.clear();
			if(formula.name().equals("Areas") || formula.name().equals("Volumes")|| formula.name().equals("Electronic Formulas")){
				createSelection(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL));
			}else{
				createWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL));
			}
			
			
		}
		
		private void createSelection(final Shell shell) {
			  shell.setText(formula.name()+" selector");
			  final Combo combo = new Combo (shell, SWT.READ_ONLY);
			  combo.add("Select a option to calculate");
			  combo.setItems (formula.inputs());
			  combo.select(0);
			  Rectangle clientArea = shell.getClientArea ();
			  combo.setBounds (clientArea.x, clientArea.y, 200, 200);
			  combo.addModifyListener(new ModifyListener() {
				
				@Override
				public void modifyText(ModifyEvent e) {
					String[] string = new String[1];
					string[0] = combo.getItem(combo.getSelectionIndex());
					formula.result(string);
					shell.dispose();
					createWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL));
				}
			});
			  shell.pack ();
			  shell.open ();
			
		}

		private void createWindow(final Shell dialog) {
			dialog.setText(formula.name()+" Formula Inputs:");
			dialog.setLayout(new GridLayout(2, false));
			Label label_info = new Label(dialog, SWT.NONE);
			label_info.setText("Leave empty to calculate");
			label_info.setLayoutData(new GridData(GridData.FILL));
			Label lb = new Label(dialog, SWT.NONE);
			lb.setText("");
			lb.setLayoutData(new GridData(GridData.CENTER));
			for (String input : formula.inputs()) {
				Label label = new Label(dialog, SWT.NONE);
				label.setText(""+input+":");
				label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				Text input_text = new Text(dialog, SWT.BORDER);
				input_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				((GridData) input_text.getLayoutData()).widthHint = 100;
				inputs_text.add(input_text);
			}

			Button ok = new Button(dialog, SWT.PUSH);
			ok.setText("Calculate");
			ok.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			ok.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					String[] results = new String[inputs_text.size()];
					for (int i = 0; i < results.length; i++) {
						if(isMultipleInput(inputs_text.get(i).getText())){
							results[i] = inputs_text.get(i).getText();
						}else if(inputs_text.get(i).getText()==null || inputs_text.get(i).getText() == "" || !isNumeric(inputs_text.get(i).getText())){
							results[i] = "";
						}else{
							results[i] = inputs_text.get(i).getText();
						}
					}
					String solution = formula.result(results);
					boolean isEmpty = false;
					for (Text result : inputs_text) {
						if(result.getText().equals("")){
							result.setText(solution);
							isEmpty = true;
						}
					}
					if(!isEmpty){
						InfoWindow.createWindow(formula.name(),solution);
						dialog.dispose();
					}
					
				}
			});

			Button cancel = new Button(dialog, SWT.PUSH);
			cancel.setText("Cancel");
			cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			cancel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					if(formula.name().equals("Areas") || formula.name().equals("Volumes")||formula.name().equals("Electronic Formulas")){
						formula.result(null);
					}
					dialog.dispose();
				}
			});
			dialog.addListener(SWT.Close, new Listener() {
			      @Override
				public void handleEvent(Event event) {
			    	  if(formula.name().equals("Areas") || formula.name().equals("Volumes")||formula.name().equals("Electronic Formulas")){
			    		  formula.result(null);
			    	  }
			      }
			});

			dialog.setDefaultButton(ok);
			dialog.pack();
		    dialog.open();
			
		}
		
		private boolean isNumeric(String str){
		    return str.matches("-?\\d+(.\\d+)?");
		}
		
		private boolean isMultipleInput(String string){
			List<String> inputs = Arrays.asList(string.split(","));
			boolean inputsAreNumbers = false;
			for (int i = 0; i <inputs.size(); i++) {
				System.out.println("inputs: " + inputs.get(i));
				if(isNumeric(inputs.get(i))){
					inputsAreNumbers=true;
				}else{
					inputsAreNumbers=false;
				}
			}
			return inputsAreNumbers;
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
		
	}