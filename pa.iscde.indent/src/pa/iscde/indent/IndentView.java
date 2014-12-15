/******************************************************************************
 * Copyright (c) 2014, Samuel José Pinto
 * All right reserved. 
 * 
 * Created on Dez 01, 2014
 * 
 *****************************************************************************/

package pa.iscde.indent;

import pa.iscde.indent.extensibility.ICodeIndenter;
import pa.iscde.indent.model.CodeIndentOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import pt.iscte.pidesco.extensibility.PidescoView;

public class IndentView implements PidescoView {

	private static IndentView instance;

	public static IndentView getInstance() {
		return instance;
	}

	@Override
	public void createContents(Composite shell, Map<String, Image> imageMap) {

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 8;

		shell.setLayout(gridLayout);

		// Indent size drop down list [1 Tab ! 2 Spaces | 3 Spaces | 4 Spaces |
		// 8 Spaces]
		Label label = new Label(shell, SWT.NULL);
		label.setText("Indent size: ");
		Combo indent = new Combo(shell, SWT.DROP_DOWN | SWT.BORDER);

		indent.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		indent.setData("1 Tab", "\t");
		indent.setData("2 Spaces", "  ");
		indent.setData("3 Spaces", "   ");
		indent.setData("4 Spaces", "    ");
		indent.setData("6 Spaces", "      ");
		indent.select(0);

		// Allow multiple breakLines (none; 1, 2, 5 10 unlimited)
		label = new Label(shell, SWT.NULL);
		label.setText("Multiple lines: ");
		Combo breakLines = new Combo(shell, SWT.DROP_DOWN | SWT.BORDER);

		breakLines.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		breakLines.setData("Unlimited", -1);
		breakLines.setData("None", 0);
		breakLines.setData("1 line", 1);
		breakLines.setData("2 line", 2);
		breakLines.setData("4 line", 4);
		breakLines.setData("10 lines", 10);
		breakLines.select(0);

		// Allow wrap lines (none; 40 chars, 70 chars, 90 chars, 120 chars, 160
		// chars)
		label = new Label(shell, SWT.NULL);
		label.setText("Wrap line text: ");
		Combo wrap = new Combo(shell, SWT.DROP_DOWN | SWT.BORDER);

		wrap.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		wrap.setData("Disabled", -1);
		wrap.setData("40 chars", 40);
		wrap.setData("70 chars", 70);
		wrap.setData("90 chars", 90);
		wrap.setData("120 chars", 120);
		wrap.setData("160 chars", 160);
		wrap.select(0);

		// Braces on end of line or next line
		label = new Label(shell, SWT.NULL);
		label.setText("Braces position: ");
		Combo braces = new Combo(shell, SWT.DROP_DOWN | SWT.BORDER);

		braces.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		braces.setData("After code", true);
		braces.setData("Same line", false);
		braces.select(0);

		Button b1 = new Button(shell, SWT.BUTTON3);
		b1.setText("Format");

		b1.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				switch (event.type) {

				case SWT.Selection:

					File file = Activator.getInstance().getOpenFile();
					String text = null;

					try {
						text = new Scanner(file, "UTF-8").useDelimiter("\\A")
								.next();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (text != null) {

						CodeIndentOptions options = new CodeIndentOptions();

						Set<ICodeIndenter> indenters = Activator.getInstance()
								.getIndenters();

						String fileExtension = file.getName().split("\\.")[1];

						// notify all subscribers
						for (ICodeIndenter l : indenters)
							if (l.suportFormat(fileExtension)) {
								l.indent(text, options);
							}
					}
					break;
				}
			}
		});
	}
}
