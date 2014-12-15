package pa.iscde.formulas.tmsas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.providers.SearchProvider;
import pa.iscde.formulas.draw.DrawEquationUtil;
import pa.iscde.formulas.draw.EquationFinder;
import pa.iscde.formulas.util.FileReadUtil;
import pa.iscde.formulas.view.FormulasView;
import pt.iscte.pidesco.extensibility.PidescoServices;


public class MathSearch implements SearchProvider {

	private PidescoServices pidescoServices;
	public static final String PLUGIN_ID = "pa.iscde.formulas.tmsas";

	public MathSearch() {
		Bundle bundle = FrameworkUtil.getBundle(SearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
	}

	@Override
	public List<Object> getResults(String text) {
		List<Object> t = new LinkedList<Object>();
		File file = FileReadUtil.loadFile(PLUGIN_ID,"formulas");
		if(file.isDirectory()){
			File[] listOfFiles = file.listFiles();
			for (File file2 : listOfFiles) {
				t.add(file2);
			}
		}
		return t;
	}

	@Override
	public Image setImage(Object object) {
		if(object instanceof File){
			return pidescoServices.getImageFromPlugin(PLUGIN_ID, "draw.gif");
		}
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		try {
			EquationFinder eq = new EquationFinder((File) object);
			if(FormulasView.getInstance()!=null){
				if(!FormulasView.getInstance().getWindowState())
					FormulasView.getInstance().setDrawEquationMode(eq);
				else
					openSWTwindow(eq,object);
			}
			else
				openSWTwindow(eq,object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openSWTwindow(EquationFinder eq, Object object) {
		final Shell shell = new Shell(new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText(((File)object).getName());
		shell.setLayout(new FillLayout());
		final ScrolledComposite scrollComposite = new ScrolledComposite(shell,
				SWT.V_SCROLL | SWT.BORDER);
		final Composite parent = new Composite(scrollComposite, SWT.NONE);
		parent.setLayout(new GridLayout(2,false));
		for (String equation : eq.getEquations().keySet()) {
			DrawEquationUtil formulaImage = new DrawEquationUtil(parent,equation); 
			Text text = new Text(parent, SWT.NONE);
			String lines = "";
			int aux = 0;
			for (Integer line : eq.getEquations().get(equation)) {
				aux ++;
				if(eq.getEquations().get(equation).size()>1 && aux!= eq.getEquations().get(equation).size())
					lines +=line+",";
				else
					lines +=line;
			}
			text.setText("Line: "+ lines);
			Label label = new Label(parent,SWT.NONE);
			label.setImage(formulaImage.getImage());
		}
		scrollComposite.setContent(parent);
		scrollComposite.setExpandVertical(true);
		scrollComposite.setExpandHorizontal(true);
		scrollComposite.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				Rectangle r = scrollComposite.getClientArea();
				scrollComposite.setMinSize(parent.computeSize(r.width,
						SWT.DEFAULT));
			}
		});
		shell.open();
		shell.setSize(600, 300);
		shell.open();
	}

}
