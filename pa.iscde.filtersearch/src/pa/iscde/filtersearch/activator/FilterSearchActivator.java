package pa.iscde.filtersearch.activator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import pa.iscde.filtersearch.model.FilterSearchModel;
import pa.iscde.filtersearch.services.FilterSearchService;


/* Classe que verifica todas as classes que implementam o servico implementado para o TextChanged da SearchTool */
public class FilterSearchActivator implements BundleActivator {
	
	private BundleContext context;
	private List<Bundle> bundleList;
	private final String OBJECT_CLASS = "objectclass=";
	private static FilterSearchModel model;

	/* Método que activa o servico de procura de todos os servicos implementadores */
	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		bundleList =  new LinkedList<Bundle>();
		model = new FilterSearchModel();
		
		String filter = "(" + OBJECT_CLASS + FilterSearchService.class.getName() + ")"; 
		context.addServiceListener(serviceListener,filter);
		
		@SuppressWarnings("unused")
		Collection<ServiceReference<FilterSearchService>> refs = context.getServiceReferences(FilterSearchService.class, null);
		
		context.addBundleListener(bundleListener);

	}
	
	
	private ServiceListener serviceListener = new ServiceListener() {	
		@Override
		public void serviceChanged(ServiceEvent event) {
			if(event.getType() == ServiceEvent.REGISTERED) {		
				ServiceReference<?> ref = event.getServiceReference();
				FilterSearchService service = (FilterSearchService) context.getService(ref);
				service.createListener(model);
				bundleList.add(ref.getBundle());
			}
		}
	};
	
	public BundleListener bundleListener = new BundleListener() {
		
		@Override
		public void bundleChanged(BundleEvent event) {
			if(event.getType() == BundleEvent.STOPPED){
				Bundle bundle = event.getBundle();
				if(bundleList.contains(bundle)){
					bundleList.remove(bundle);
				}
			}
			
		}
	};


	@Override
	public void stop(BundleContext context) throws Exception {

	}
	
	public static FilterSearchModel Model(){
		if(model == null)
			model = new FilterSearchModel();
		return model;
	}

}
