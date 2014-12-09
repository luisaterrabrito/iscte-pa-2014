package pa.iscde.packagediagram.internal;

import javax.inject.Inject;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Image;

import pt.iscte.pidesco.projectbrowser.extensibility.ProjectBrowserFilter;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class ProjectBrowserFilterPackageDiagram implements ProjectBrowserFilter {

	public static final String ID = "de.vogella.zest.first.ProjectBrowserFilterPackageDiagram";
	private static final String EXT_POINT_FILTER = "pt.iscte.pidesco.projectbrowser.filter";

	
	
	public ProjectBrowserFilterPackageDiagram() {
		

		System.out.println("ola");
	  // do something
		
//		IConfigurationElement[] registry = Platform.getExtensionRegistry().
//		  getConfigurationElementsFor(EXT_POINT_FILTER); 
//		
//		for(IConfigurationElement x:registry)
//			System.out.println(x);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean include(SourceElement element, PackageElement parent) {

		// TODO Auto-generated method stub
		return false;
	}

	@Inject 
	public void doSomething(IExtensionRegistry registry){
	  // do something
//	  registry.getConfigurationElementsFor(EXT_POINT_FILTER);
	}

}
