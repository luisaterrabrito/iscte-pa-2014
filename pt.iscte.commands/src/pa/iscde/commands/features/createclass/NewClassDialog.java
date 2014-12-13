package pa.iscde.commands.features.createclass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.utils.ExtensionPointsIDS;
import pa.iscde.commands.utils.Labels;
import pt.iscte.pidesco.extensibility.PidescoServices;

final class NewClassDialog extends TitleAreaDialog {

	private Text className;
	private Text packageName;
	private Button radioPublicModifier;;
	private Button radioPackageModifier;

	private Button checkBoxAbstractModifier;
	private Button checkBoxFinalModifier;
	private PidescoServices services;

	protected NewClassDialog(Shell shell) {
		super(shell);
		BundleContext context = CommandsController.getContext();
		ServiceReference<PidescoServices> ref = context
				.getServiceReference(PidescoServices.class);
		services = context.getService(ref);
	}

	@Override
	public void create() {
		super.create();
		setTitle(Labels.CREATECLASS_LBL);
		setDefaultMessageTitle();
	}

	private void setDefaultMessageTitle() {
		setMessage(Labels.CREATECLASSINFO_LBL, IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		GridData fieldsLayout = new GridData();
		fieldsLayout.horizontalAlignment = SWT.FILL;
		fieldsLayout.grabExcessHorizontalSpace = true;
		fieldsLayout.verticalAlignment = SWT.TOP;
		fieldsLayout.grabExcessVerticalSpace = false;
		fieldsLayout.heightHint = 20;

		Composite comp = new Composite(container, SWT.BORDER);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		comp.setLayout(new GridLayout(2, false));

		Label packageLabel = new Label(comp, SWT.NONE);
		packageLabel.setText(Labels.PACKAGE_LBL);

		packageName = new Text(comp, SWT.BORDER);
		packageName.setLayoutData(fieldsLayout);
		packageName.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				validatePackageName();

			}
		});

		Label nameLabel = new Label(comp, SWT.NONE);
		nameLabel.setText(Labels.NAME_LBL);

		className = new Text(comp, SWT.BORDER);
		className.setLayoutData(fieldsLayout);
		className.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				validClassName();

			}
		});

		Label modifiersLabel = new Label(comp, SWT.NONE);
		modifiersLabel.setText(Labels.MODIFIERS_LBL);

		Composite c = new Composite(comp, SWT.NONE);
		c.setLayout(new FillLayout());
		c.setLayoutData(fieldsLayout);
		radioPublicModifier = new Button(c, SWT.RADIO);
		radioPublicModifier.setText(Labels.PUBLICMODIFIER_LBL);
		radioPublicModifier.setSelection(true);
		radioPackageModifier = new Button(c, SWT.RADIO);
		radioPackageModifier.setText(Labels.PACKAGEMODIFIER_LBL);

		checkBoxAbstractModifier = new Button(c, SWT.CHECK);
		checkBoxAbstractModifier.setText("Abstract");
		checkBoxFinalModifier = new Button(c, SWT.CHECK);
		checkBoxFinalModifier.setText(Labels.FINALMODIFIER_LBL);

		return container;
	}

	@Override
	protected void okPressed() {

		if (validClassName() && validatePackageName() && !fileExists()) {
			try {
				createClass();
				services.runTool(
						ExtensionPointsIDS.REFRESH_EXPLORER_ID.getID(), false);
			} catch (IOException e) {
				System.err
						.println("An error secure while creating the java class file.");
			}
			super.okPressed();
		}
	}

	private boolean fileExists() {
		if (GetFile().exists()) {
			setMessage(Labels.FILEEXISTS_LBL, IMessageProvider.ERROR);
			return true;
		}
		return false;
	}

	private void createClass() throws IOException {

		BufferedWriter bw = null;
		try {

			File file = GetFile();

			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				bw = new BufferedWriter(fw);
				bw.write(createClassString().toString());
			}
		} finally {
			if (bw != null) {
				bw.close();
			}
		}

	}

	private File GetFile() {
		String folders = Joiner.on("/").join(
				Splitter.on(".").split(packageName.getText()));

		String workspace = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toString();

		File file = new File(workspace + "/" + folders + "/"
				+ className.getText() + ".java");
		return file;
	}

	private StringBuilder createClassString() {
		StringBuilder sb = new StringBuilder(100);

		if (packageName.getText().length() != 0)
			sb.append("package " + packageName.getText() + ";"
					+ System.lineSeparator() + System.lineSeparator());

		if (radioPublicModifier.getSelection())
			sb.append("public ");

		if (checkBoxAbstractModifier.getSelection())
			sb.append("abstract ");

		if (checkBoxFinalModifier.getSelection())
			sb.append("final ");

		sb.append("class " + className.getText() + " {"
				+ System.lineSeparator() + System.lineSeparator());

		// o contrutor
		sb.append("\tpublic " + className.getText() + "() {"
				+ System.lineSeparator() + "\t\t// TODO Code here "
				+ System.lineSeparator());

		sb.append("\t}" + System.lineSeparator() + System.lineSeparator());

		sb.append("}");
		return sb;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 280);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.CREATECLASS_LBL);
	}

	private boolean validClassName() {
		if (className.getText().length() == 0
		// começar por uma letra e não ter espaços em branco
				|| !className.getText().matches("^[A-Za-z](\\w)*")) {

			setMessage(Labels.CREATECLASSERROR_LBL, IMessageProvider.ERROR);
			return false;
		}

		setDefaultMessageTitle();
		return true;
	}

	private boolean validatePackageName() {
		if (packageName.getText().length() == 0) {
			setDefaultMessageTitle();
			return true;
		}

		// verifica se o package te o formato correcto:
		// package1.packageInside.Package
		boolean result = packageName.getText().matches("(\\w|\\.\\w)*");
		if (!result) {
			setMessage(Labels.CREATECLASSPACKAGEERROR_LBL,
					IMessageProvider.ERROR);
		} else {
			setDefaultMessageTitle();
		}

		return result;
	}
}
