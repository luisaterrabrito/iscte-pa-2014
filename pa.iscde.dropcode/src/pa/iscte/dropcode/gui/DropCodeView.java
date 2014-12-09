package pa.iscte.dropcode.gui;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import pa.iscde.dropcode.DropCodeActivator;
import pa.iscde.dropcode.dropreflection.DropClass;
import pa.iscde.dropcode.dropreflection.DropField;
import pa.iscde.dropcode.dropreflection.DropMethod;
import pa.iscde.dropcode.services.DropBar;
import pt.iscte.pidesco.extensibility.PidescoView;

public class DropCodeView implements PidescoView {

	private static DropCodeView instance;

	public static DropCodeView getInstance() {
		return instance;
	}

	private Composite bars;
	private DropClass dropClass;
	private LinkedList<DropBarContainer> tabComps;

	@Override
	public void createContents(Composite comp,
			java.util.Map<String, Image> images) {

		ClosableLabel.image_up = images.get("x.png");
		ClosableLabel.image_down = images.get("x2.png");

		tabComps = new LinkedList<DropBarContainer>();

		instance = this;

		ScrolledComposite scrollComp = new ScrolledComposite(comp, SWT.BORDER
				| SWT.V_SCROLL);
		scrollComp.setExpandHorizontal(true);
		scrollComp.setExpandVertical(false);
		// scrollComp.setBounds(0, 0, 200, 200);

		bars = new Composite(scrollComp, SWT.NONE);
		bars.setLayout(new RowLayout(SWT.VERTICAL));
		scrollComp.setContent(bars);

		tabComps.add(new DropBarContainer(bars, new DropBar() {

			@Override
			public String getName() {
				return "Fields";
			}

			@Override
			public void createContents(Composite parent) {
				parent.setLayout(new FillLayout(SWT.VERTICAL));
				if (dropClass != null)
					for (DropField df : dropClass.getFields()) {
						new DropRow(parent, SWT.NONE, df);
					}
			}
		}));

		tabComps.add(new DropBarContainer(bars, new DropBar() {

			@Override
			public String getName() {
				return "Constructors";
			}

			@Override
			public void createContents(Composite parent) {
				parent.setLayout(new FillLayout(SWT.VERTICAL));
				if (dropClass != null)
					for (DropMethod df : dropClass.getConstructors()) {
						new DropRow(parent, SWT.NONE, df);
					}
			}
		}));

		tabComps.add(new DropBarContainer(bars, new DropBar() {

			@Override
			public String getName() {
				return "Methods";
			}

			@Override
			public void createContents(Composite parent) {
				parent.setLayout(new FillLayout(SWT.VERTICAL));
				if (dropClass != null)
					for (DropMethod df : dropClass.getMethods()) {
						new DropRow(parent, SWT.NONE, df);
					}
			}
		}));

		addPluginDropbars(scrollComp);

		// bars.setBackgroundImage(images.get("background.jpg"));
		bars.layout(true, true);
	}

	public void updateContent() {
		System.out.println("DropCodeView.update()");
		dropClass = DropCodeActivator.getDropClass();

		for (DropBarContainer d : tabComps) {
			d.updateContents();
			System.out.println(d.getDropBar().getName() + " update()");
		}
		bars.setSize(bars.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		// setHeight(fields.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}

	private void addPluginDropbars(Composite parent) {
		IExtensionPoint extp = Platform.getExtensionRegistry()
				.getExtensionPoint("pa.iscde.dropcode.dropbars"); // TODO

		if (extp != null) // TODO
			for (IExtension ext : extp.getExtensions()) {

				String name = ext.getContributor().getName();
				// String id = ext.getUniqueIdentifier();
				// String label = ext.getLabel();

				IConfigurationElement e = ext.getConfigurationElements()[0];
				try {
					tabComps.add(new DropBarContainer(parent, (DropBar) e
							.createExecutableExtension("class")));
					JOptionPane.showMessageDialog(null, "Extension Loaded: " + name);
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
	}
}
