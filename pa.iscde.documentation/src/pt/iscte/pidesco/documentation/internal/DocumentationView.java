package pt.iscte.pidesco.documentation.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.documentation.service.ITagContentProvider;

public class DocumentationView implements PidescoView {

	private static final String TAGS_EXT_POINT_ID = "pa.iscde.documentation.tags";
	
	private static DocumentationView instance;

	private JavaEditorServices javaServices;
	//private ProjectBrowserServices browserServices;

	private Browser browser;
	private ObjectDoc classDoc;
	private Map<String, ITagContentProvider> activeTags = new HashMap<String, ITagContentProvider>();

	
	public DocumentationView() {
		Bundle bundle = FrameworkUtil.getBundle(DocumentationView.class);
		BundleContext context  = bundle.getBundleContext();
		
		ServiceReference<JavaEditorServices> refEditor = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(refEditor);
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;

		browser = new Browser(viewArea, SWT.NONE);
		
		if (javaServices.getOpenedFile() != null){
			this.fillView();
		}
	}

	public void fillView() {
		classDoc = new ObjectDoc();
		
		this.cleanView();

		if (javaServices.getOpenedFile() != null) {
			ASTVisitor visitor = findDocumentation();

			javaServices.parseFile(javaServices.getOpenedFile(), visitor);

			// Apanha e escreve todas as tags
			// mas se apanhar uma tag de ponto de extensão, utiliza essa!!!
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			for (IExtension ext : reg.getExtensionPoint(TAGS_EXT_POINT_ID).getExtensions()) {
				for (IConfigurationElement member : ext.getConfigurationElements()) {
					try {
						String tagName = member.getAttribute("name");
						ITagContentProvider provider = (ITagContentProvider) member.createExecutableExtension("provider");

						activeTags.put(tagName, provider);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}

			// Set Text to the Browser in HTML
			browser.setText(toHTML());
		}
	}
	
	public void cleanView() {
		StringBuilder sb = new StringBuilder();
		sb.append("<br><font size='4'><b>Nenhum objeto em contexto!</b></font></br>");
		sb.append("<br>Por favor, abra um ficheiro do Project Explorer...</br>");

		browser.setText(sb.toString());
		browser.redraw();
	}
	
	/**
	 * Method responsable to visit every node to get the comments and tags
	 * @return ASTVisitor
	 */
	private ASTVisitor findDocumentation() {
		ASTVisitor visitor = new ASTVisitor() {

			/**
			 * Method that visit every node of Javadoc
			 * @return
			 */
			@Override
			@SuppressWarnings("unchecked")
			public boolean visit(Javadoc node) {
				// Condition to get properties of the class or interface
				if (node.getParent() instanceof TypeDeclaration) {
					classDoc.setFullName(((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());

					for (TagElement tag : (List<TagElement>) node.tags()) {
						String str = "";
						for (Object fragment : tag.fragments())
							str += fragment.toString() + " ";
						
						// get tag class description
						if (tag.getTagName() == null)
							classDoc.setComment(str);
						else {
							// get all existing tags from class
							classDoc.getTags().put(tag.getTagName(), str);
						}
					}
				}
				
				// Condition to get properties of a contrutor/method
				if (node.getParent() instanceof MethodDeclaration) {
					MethodDeclaration method = (MethodDeclaration) node.getParent();
					
					if ( method.isConstructor() ) {
						ConstrutorDoc construtorDoc = new ConstrutorDoc();
						construtorDoc.setName(method.getName().getFullyQualifiedName());

						// TODO: getSignature from Construtor
						// By this way, with AST Node it's very difficult to get
						//methodDoc.setSignature(((MethodDeclaration) node.getParent()).getName().getIdentifier());

						for (TagElement tag : (List<TagElement>) node.tags()) {
							String str = "";
							for (Object fragment : tag.fragments())
								str += fragment.toString() + " ";
							
							// get tag construtor description
							if (tag.getTagName() == null)
								construtorDoc.setComment(str);
							else {
								// get all existing tags from construtor
								construtorDoc.getTags().put(tag.getTagName(), str);
							}
						}
						
						// Add Construtor to Class/Interface
						classDoc.getConstrutors().add(construtorDoc);
					
					} else {
						MethodDoc methodDoc = new MethodDoc();
						methodDoc.setName(method.getName().getFullyQualifiedName());

						// TODO: getSignature from Construtor
						// By this way, with AST Node it's very difficult to get
						//methodDoc.setSignature(((MethodDeclaration) node.getParent()).getName().getIdentifier());
						
						for (TagElement tag : (List<TagElement>) node.tags()) {
							String str = "";
							for (Object fragment : tag.fragments())
								str += fragment.toString() + " ";
							
							// get tag method description
							if (tag.getTagName() == null)
								methodDoc.setComment(str);
							else {
								// get all existing tags from method
								methodDoc.getTags().put(tag.getTagName(), str);
							}
						}
						
						// Add Method to Class/Interface
						classDoc.getMethods().add(methodDoc);
					}
				}
				
				return false;
			}
		};

		return visitor;
	}
	
	private String toHTML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<br><font size='5'><b>Classe: </b>" + classDoc.getFullName() + "</font></br>");
		sb.append("<br><font size='2'>" + classDoc.getComment() + "</font></br>");
		sb.append("</br>");

		Iterator<Entry<String, String>> itObjectTags = classDoc.getTags().entrySet().iterator();
		while (itObjectTags.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) itObjectTags.next();
			
			if (activeTags.containsKey("@" + pairs.getKey())) {
				sb.append("<br><b>" + pairs.getKey() + ":</b> " + activeTags.get(pairs.getKey()).getHtml(pairs.getValue()) + "</br>");
			} else {
				sb.append("<br><b>" + pairs.getKey() + ":</b> " + pairs.getValue() + "</br>");
			}
		}

		sb.append("<br>------------------------------------------------------------------------------------------</br>");
		sb.append("</br>");
		for (ConstrutorDoc construtor : classDoc.getConstrutors()) {
			sb.append("<br><font size='3'><b>Construtor: </b>" + construtor.getName() + "</font></br>");
			//sb.append("<br><font size='1'>" + construtor.getSignature() + "</font></br>");
			sb.append("<br><font size='2'>" + construtor.getComment() + "</font></br>");
			sb.append("</br>");

			Iterator<Entry<String, String>> itMethodTags = construtor.getTags().entrySet().iterator();
			while (itMethodTags.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) itMethodTags.next();
				
				if (activeTags.containsKey("@" + pairs.getKey())) {
					sb.append("<br><b>" + pairs.getKey() + ":</b> " + activeTags.get(pairs.getKey()).getHtml(pairs.getValue()) + "</br>");
				} else {
					sb.append("<br><b>" + pairs.getKey() + ":</b> " + pairs.getValue() + "</br>");
				}
			}
			sb.append("<br>------------------------------------------------------------------------------------------</br>");
		}

		sb.append("<br>------------------------------------------------------------------------------------------</br>");
		sb.append("</br>");
		for (MethodDoc method : classDoc.getMethods()) {
			sb.append("<br><font size='3'><b>Método: </b>" + method.getName() + "</font></br>");
			//sb.append("<br><font size='1'>" + method.getSignature() + "</font></br>");
			sb.append("<br><font size='2'>" + method.getComment() + "</font></br>");
			sb.append("</br>");

			Iterator<Entry<String, String>> itMethodTags = method.getTags().entrySet().iterator();
			while (itMethodTags.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) itMethodTags.next();
				
				if (activeTags.containsKey("@" + pairs.getKey())) {
					sb.append("<br><b>" + pairs.getKey() + ":</b> " + activeTags.get(pairs.getKey()).getHtml(pairs.getValue()) + "</br>");
				} else {
					sb.append("<br><b>" + pairs.getKey() + ":</b> " + pairs.getValue() + "</br>");
				}
			}
			sb.append("<br>------------------------------------------------------------------------------------------</br>");
		}

		return sb.toString();
	}
	
	public static DocumentationView getInstance() {
		return instance;
	}

}
