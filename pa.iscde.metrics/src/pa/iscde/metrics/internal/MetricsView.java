package pa.iscde.metrics.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.metrics.extensibility.DefaultVisitor;
import pa.iscde.metrics.extensibility.Metricable;
import pa.iscde.metrics.internal.metrics.AttributesMetric;
import pa.iscde.metrics.internal.metrics.ClassesMetric;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class MetricsView implements PidescoView {

	private static final String VIEW_ID = "pa.iscde.metrics";
	private static final String EXT_POINT_ID = "pa.iscde.metrics.createMetric";
	private static MetricsView instance;
	// serve para abrir views
	private static PidescoServices services;

	// serve para aceder ao ficheiro aberto
	private JavaEditorServices javaServices;
	private ProjectBrowserServices browserServices;
	private MetricsVisitor visitor ;
	private Table table;

	// Quando a classe é compilada pela primeira vez o bloco static é executado
	static {
		Bundle bundle = FrameworkUtil.getBundle(MetricsView.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<PidescoServices> reference = context
				.getServiceReference(PidescoServices.class);
		services = context.getService(reference);
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		// Definir a instancia da classe MetricsView como a instancia da view
		// que esta a ser criada
		instance = this;
		table = new Table(viewArea, SWT.NONE);
		TableColumn metricName = new TableColumn(table, SWT.NONE);
		TableColumn metricValue = new TableColumn(table, SWT.NONE);

		metricName.setText("Metric name");
		metricName.setWidth(200);
		metricValue.setText("Value");
		metricValue.setWidth(50);

		table.setHeaderVisible(true);

		refresh();
	}

	//metodo recursivo para vierificar se os filhos da root são packages ou classes e guarda-os numa lista
	public List<SourceElement> listElements(PackageElement root) {
		List<SourceElement> elements = new ArrayList<SourceElement>();
		SortedSet<SourceElement> rootChildren = root.getChildren();
		for (SourceElement i : rootChildren) {
			if (i.isPackage())
				elements.addAll(listElements((PackageElement) i));
			elements.add(i);
		}
		return elements;
	}

	//vai buscar instancia do objecto metrics view
	public static MetricsView getInstance() {
		if (instance == null)
			services.openView(VIEW_ID);
		return instance;
	}
	
	public MetricsVisitor getVisitor(){
		return visitor;
	}
	
	public ProjectBrowserServices getBrowserServices() {
		return browserServices;
	}

	public void refresh() {
		table.removeAll();
		
		visitor = new MetricsVisitor();

		Bundle bundle = FrameworkUtil.getBundle(MetricsView.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> reference = context
				.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(reference);

		//vai buscar o project browser services que disponibiliza o root package
		browserServices = context.getService(context
				.getServiceReference(ProjectBrowserServices.class));
		PackageElement root = browserServices.getRootPackage();

		List <SourceElement> elements = listElements(root);
		for (SourceElement i : elements) {
			if(i.isClass())
				javaServices.parseFile(i.getFile(), visitor);
		}
		
		//vai buscar o registo de todas as extensões
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		//vai buscar as extensoes para o meu extension point
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_ID).getExtensions()) {

			for(IConfigurationElement member : ext.getConfigurationElements()) {
				try {
					//cria um objecto da classe indicada no atributo 'implementation'(ex.CalculateMetric) 
					//que implementa a interface Metricable
					Metricable m = (Metricable)  member.createExecutableExtension("implementation");
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(0, member.getAttribute("name"));
					tableItem.setText(1, "" +  m.calculateMetric(visitor));

				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
