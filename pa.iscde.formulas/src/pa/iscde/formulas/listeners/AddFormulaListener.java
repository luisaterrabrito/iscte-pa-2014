package pa.iscde.formulas.listeners;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pa.iscde.formulas.ReadFormulaFromFile;


public class AddFormulaListener implements SelectionListener{

	private int inputsNumber =0;
	private String [] inputsName;
	private String formulaNameString;
	private String categoryNameString;
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

		Label category = new Label(dialog, SWT.NONE);
		category.setText("Category: ");
		category.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Combo c = new Combo(dialog, SWT.READ_ONLY);
		c.setBounds(50, 50, GridData.FILL_HORIZONTAL, 65);
		String items[] = { "Basics", "Finance", "Statistic", "Engineering"};
		c.setItems(items);

		//	final Text input_text_category = new Text(dialog, SWT.BORDER);
		//	input_text_category.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//	((GridData) input_text_category.getLayoutData()).widthHint = 100;

		Label formulaName = new Label(dialog, SWT.NONE);
		formulaName.setText("Formula Name: ");
		formulaName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Text input_text_name= new Text(dialog, SWT.BORDER);
		input_text_name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		((GridData) input_text_name.getLayoutData()).widthHint = 100;

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
				categoryNameString=c.getText();
				System.out.println("category: " + categoryNameString);
				formulaNameString=input_text_name.getText();
				if(isNumeric(input_text.getText())){
					inputsNumber = Integer.parseInt(input_text.getText());
					createFormulaWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL), inputsNumber);
					dialog.dispose();
				}else{
					MessageBox information = new MessageBox(dialog, SWT.ICON_QUESTION | SWT.OK);
					information.setText("Inputs invalid");
					information.setMessage("Inputs number must be numeric");
					information.open();
				}

			}
		});

		Button cancel = new Button(dialog, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				dialog.dispose();
			}
		});
		dialog.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
			}
		});

		dialog.setDefaultButton(ok);
		dialog.pack();
		dialog.open();

	}

	private void createFormulaWindow(final Shell shell, final int inputsNumber) {
		shell.setText("Formula Specification");
		shell.setLayout(new GridLayout(2, false));

		for (int i = 0; i < inputsNumber; i++) {
			Label label = new Label(shell, SWT.NONE);
			int j = i+1;
			label.setText("Input " + j +":");
			label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			Text input_text = new Text(shell, SWT.BORDER);
			input_text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			input_text.setSize(100, 50);
			((GridData) input_text.getLayoutData()).widthHint = 100;
			inputs_text.add(input_text);
		}
		shell.setLayout(new GridLayout(1, false));
		Label label_algorithm = new Label(shell, SWT.NONE);
		label_algorithm.setText("Type your algorithm");
		label_algorithm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text t_algorithm = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		t_algorithm.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label_java_code = new Label(shell, SWT.NONE);
		label_java_code.setText("Type your java code");
		label_java_code.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Text t_code = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		t_code.setLayoutData(new GridData(GridData.FILL_BOTH));

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
				System.out.println("codigo: " +  t_code.getText() + " algoritmo: " + t_algorithm.getText());
				try {
					addFormulaToFile(t_code.getText(),t_algorithm.getText(),formulaNameString,categoryNameString,inputsName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//createCodeWindow(new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL), inputsName);
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
		shell.open();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	private boolean isNumeric(String str){
		return str.matches("-?\\d+(.\\d+)?");
	}

	private void addFormulaToFile(String javaCode, String algorithm, String formulaNameString, String categoryNameString, String[] inputsName) {
		new ReadFormulaFromFile("");
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("formulas\\NewFormulas.txt");
		Scanner s = new Scanner(is);
		String string ="";
		while(s.hasNext()){
			string += s.nextLine();
		}
		s.close();

		//tentativa 1
//		BufferedWriter writer =null;
//		try{
//			writer = new BufferedWriter(new FileWriter("formulas\\NewFormulas.txt"));
//			writer.write(string+System.lineSeparator()+javaCode);
//		}catch(IOException e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(writer!=null)
//					writer.close();
//			}catch (IOException e){
//				e.printStackTrace();
//			}
//		}
		
		//tentativa 2
//		FileOutputStream fop = null;
//		File file;
//		String content= string+System.lineSeparator()+javaCode;
//		try{
//			file = new File("formulas\\NewFormulas.txt");
//			fop= new FileOutputStream(file);
//			byte[]contentInBytes = content.getBytes();
//			fop.write(contentInBytes);
//			fop.flush();
//			fop.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}finally{
//			try{
//				if(fop!=null){
//					fop.close();
//				}
//			}catch(IOException e){
//				e.printStackTrace();
//			}
//		}
		
		
		//tentativa 3
//		try {
//			PrintWriter writer = new PrintWriter(new File(classloader.getResource("formulas\\NewFormulas.txt").getPath()));
//			//PrintWriter writer = new PrintWriter(new File(this.getClass().getResource("formulas\\NewFormulas.txt").getPath()));
//			writer.println(string+System.lineSeparator()+javaCode);
//			writer.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			FileWriter writer = new FileWriter(new File(classloader.getResource("formulas\\NewFormulas.txt").getPath()),true);
//			PrintWriter out = new PrintWriter(writer);
//			out.println(string+System.lineSeparator()+javaCode);
//			out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	
	}
}

