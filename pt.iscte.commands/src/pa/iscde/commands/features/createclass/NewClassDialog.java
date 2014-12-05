package pa.iscde.commands.features.createclass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import pa.iscde.commands.utils.Labels;

final class NewClassDialog extends TitleAreaDialog {

	private Text className;
	private Text packageName;
	private Button radioPublicModifier;;
	private Button radioPackageModifier;

	private Button checkBoxAbstractModifier;
	private Button checkBoxFinalModifier;

	protected NewClassDialog(Shell shell) {
		super(shell);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		super.create();
		setTitle(Labels.CREATECLASS_LBL);
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

		Label packageLabel = new Label(container, SWT.NONE);
		packageLabel.setText(Labels.PACKAGE_LBL);

		packageName = new Text(container, SWT.BORDER);
		packageName.setLayoutData(fieldsLayout);

		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText(Labels.NAME_LBL);

		className = new Text(container, SWT.BORDER);
		className.setLayoutData(fieldsLayout);

		Label modifiersLabel = new Label(container, SWT.NONE);
		modifiersLabel.setText(Labels.MODIFIERS_LBL);

		Composite c = new Composite(container, SWT.NONE);
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
		try {
			createClass();
		} catch (IOException e) {
			System.err
					.println("An error secure while creating the java class file.");
		}
		super.okPressed();
	}

	private void createClass() throws IOException {

		BufferedWriter bw = null;
		try {

			String folders = Joiner.on("/").join(
					Splitter.on(".").split(packageName.getText()));

			String workspace = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toString();

			File file = new File(workspace + "/" + folders + "/"
					+ className.getText() + ".java");

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(createClassString().toString());

			if (!file.exists()) {
				file.createNewFile();
			}

		} finally {
			if (bw != null) {
				bw.close();
			}
			// TODO Fazer o refresh no project explorer
		}

	}

	private StringBuilder createClassString() {
		StringBuilder sb = new StringBuilder(100);

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
		return new Point(450, 580);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.CREATECLASS_LBL);
	}

}
