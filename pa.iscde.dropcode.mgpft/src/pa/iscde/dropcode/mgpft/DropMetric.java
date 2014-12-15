package pa.iscde.dropcode.mgpft;

import java.io.File;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.metrics.extensibility.Metricable;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DropMetric implements Metricable{

	int numberOfAnnotations;
	
	public DropMetric(){
		numberOfAnnotations = 0;
	}
	
	//TODO @Override
	public String metricName(){
		return "Number of Annotations";
	}
	
	@Override
	public double calculateMetric() {
		
		Bundle bundle = FrameworkUtil.getBundle(JavaEditorServices.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> reference = context
				.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaEditor = context.getService(reference);		
		
		File file = javaEditor.getOpenedFile();
		javaEditor.parseFile(file, new ASTVisitor(){
			
			@Override
			public boolean visit(AnnotationTypeDeclaration node) {
				numberOfAnnotations++;
				return true;
			}
		});
		
		return numberOfAnnotations;
	}

}
