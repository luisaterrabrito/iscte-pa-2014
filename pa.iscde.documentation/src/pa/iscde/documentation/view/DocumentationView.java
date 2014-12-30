package pa.iscde.documentation.view;

import java.util.ArrayList;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.documentation.extension.IDocumentationExportProvider;
import pa.iscde.documentation.extension.ITagContentProvider;
import pa.iscde.documentation.struture.ConstrutorDoc;
import pa.iscde.documentation.struture.MethodDoc;
import pa.iscde.documentation.struture.ObjectDoc;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class DocumentationView implements PidescoView {

	private static final String TAG_EXT_POINT_ID = "pa.iscde.documentation.tags";
	private static final String EXP_EXT_POINT_ID = "pa.iscde.documentation.exports";
	
	private static DocumentationView instance;
	
	private JavaEditorServices javaServices;

	private Browser browser;
	private Button btnExport;
	private ObjectDoc objectDoc;
	private Map<String, ITagContentProvider> activeTags = new HashMap<String, ITagContentProvider>();
	private List<IDocumentationExportProvider> exportToList = new ArrayList<IDocumentationExportProvider>();
	
	public DocumentationView() {
		Bundle bundle = FrameworkUtil.getBundle(DocumentationView.class);
		BundleContext context  = bundle.getBundleContext();
		
		ServiceReference<JavaEditorServices> refEditor = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(refEditor);
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		
		viewArea.setLayout(new GridLayout(1, false));
		
		btnExport = new Button(viewArea, SWT.PUSH);
		btnExport.setText("Export As...");
		btnExport.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		
		browser = new Browser(viewArea, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.fillView();
	}

	public void fillView() {
		objectDoc = new ObjectDoc();
		
		this.cleanView();

		if (javaServices.getOpenedFile() != null) {
			btnExport.setEnabled(true);
			
			ASTVisitor visitor = findDocumentation();

			javaServices.parseFile(javaServices.getOpenedFile(), visitor);

			// Apanha e escreve todas as tags
			// mas se apanhar uma tag de ponto de extensão, utiliza essa!!!
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			for (IExtension ext : reg.getExtensionPoint(TAG_EXT_POINT_ID).getExtensions()) {
				for (IConfigurationElement member : ext.getConfigurationElements()) {
					try {
						String tagName = member.getAttribute("name");
						ITagContentProvider provider = (ITagContentProvider) member.createExecutableExtension("provider");

						activeTags.put("@" + tagName, provider);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}

			for (IExtension ext : reg.getExtensionPoint(EXP_EXT_POINT_ID).getExtensions()) {
				for (IConfigurationElement member : ext.getConfigurationElements()) {
					try {
						IDocumentationExportProvider provider = (IDocumentationExportProvider) member.createExecutableExtension("provider");

						exportToList.add(provider);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}

			// Set Text to the Browser in HTML
			browser.setText(toHTML());
			
			// Set Export Button
			btnExport.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					switch (e.type) {
						case SWT.Selection:
							final Display display = Display.getDefault();
							final Shell shell = new Shell(display);
							
							FileDialog dialog = new FileDialog(shell, SWT.SAVE);
							dialog.setText("Export To");
							
							String[] filterNames = new String[exportToList.size()];
							String[] filterExtensions = new String[exportToList.size()];
							for (int i = 0; i < exportToList.size(); i++) {
								filterNames[i] = exportToList.get(i).getFilterName();
								filterExtensions[i] = exportToList.get(i).getFilterExtension();
							}
							
							dialog.setFilterNames(filterNames); 
						    dialog.setFilterExtensions(filterExtensions);
						    dialog.setFilterPath(System.getProperty("user.home"));
						    dialog.setFileName("exportTo");
						    
						    String fullFileName = dialog.open();

						    if (fullFileName != null) {
						    	try {
									exportToList.get(dialog.getFilterIndex()).saveToFile(fullFileName, objectDoc);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
						    }
						    
							while (!shell.isDisposed()) {
								if (!display.readAndDispatch())
									display.sleep();
							}
							display.dispose();
							
							break;
					}
				}
			});

			//////////////////////////////////////////////////////

		}
	}
	
	public void cleanView() {
		btnExport.setEnabled(false);
		
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
					objectDoc.setFullName(((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());

					for (TagElement tag : (List<TagElement>) node.tags()) {
						String str = "";
						for (Object fragment : tag.fragments())
							str += fragment.toString() + " ";
						
						// get tag class description
						if (tag.getTagName() == null)
							objectDoc.setComment(str);
						else {
							// get all existing tags from class
							if ( objectDoc.getTags().containsKey(tag.getTagName()) ) {
								objectDoc.getTags().get(tag.getTagName()).add(str);
							}
							else {
								List<String> arr = new ArrayList<String>();
								arr.add(str);
								
								objectDoc.getTags().put(tag.getTagName(), arr);
							}
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
								if ( construtorDoc.getTags().containsKey(tag.getTagName()) ) {
									construtorDoc.getTags().get(tag.getTagName()).add(str);
								}
								else {
									List<String> arr = new ArrayList<String>();
									arr.add(str);
									
									construtorDoc.getTags().put(tag.getTagName(), arr);
								}
							}
						}
						
						// Add Construtor to Class/Interface
						objectDoc.getConstrutors().add(construtorDoc);
					
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
								if ( methodDoc.getTags().containsKey(tag.getTagName()) ) {
									methodDoc.getTags().get(tag.getTagName()).add(str);
								}
								else {
									List<String> arr = new ArrayList<String>();
									arr.add(str);
									
									methodDoc.getTags().put(tag.getTagName(), arr);
								}
							}
						}
						
						// Add Method to Class/Interface
						objectDoc.getMethods().add(methodDoc);
					}
				}
				
				return false;
			}
		};

		return visitor;
	}
	
	private String toHTML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<br><font size='6'><b><i>Classe: </i></b>" + objectDoc.getFullName() + "</font>");
		if ( objectDoc.getComment().length() != 0 )
			sb.append("<br><font size='2'>" + objectDoc.getComment() + "</font>");
		sb.append("<br>");

		if ( !objectDoc.getTags().isEmpty() ) {
			writeTagsToHtml(sb, objectDoc.getTags().entrySet().iterator());
		}
		sb.append("<br>------------------------------------------------------------------------------------------");

		if ( !objectDoc.getConstrutors().isEmpty() ) {
			for (ConstrutorDoc construtor : objectDoc.getConstrutors()) {
				sb.append("<br><font size='4'><b><i>Construtor: </i></b>" + construtor.getName() + "</font>");
				//sb.append("<br><font size='1'>" + construtor.getSignature() + "</font></br>");
				if ( construtor.getComment() != null && construtor.getComment().length() > 0 )
					sb.append("<br><font size='2'>" + construtor.getComment() + "</font>");
				sb.append("<br>");
	
				writeTagsToHtml(sb, construtor.getTags().entrySet().iterator());
			}
			sb.append("<br>------------------------------------------------------------------------------------------");
		}

		if ( !objectDoc.getMethods().isEmpty() ) {
			for (MethodDoc method : objectDoc.getMethods()) {
				sb.append("<br><font size='4'><b><i>Método: </i></b>" + method.getName() + "</font>");
				//sb.append("<br><font size='1'>" + method.getSignature() + "</font></br>");
				if ( method.getComment() != null && method.getComment().length() > 0 )
					sb.append("<br><font size='2'>" + method.getComment() + "</font>");
				sb.append("<br>");
	
				writeTagsToHtml(sb, method.getTags().entrySet().iterator());
			}
			sb.append("<br>------------------------------------------------------------------------------------------");
		}
		
		return sb.toString();
	}
	
	private void writeTagsToHtml(StringBuilder sb, Iterator<Entry<String, List<String>>> itTags) {
		if ( itTags.hasNext() ) {
			while (itTags.hasNext()) {
				Map.Entry<String, List<String>> pairs = (Map.Entry<String, List<String>>) itTags.next();
				
				if (activeTags.containsKey(pairs.getKey())) {
					sb.append(activeTags.get(pairs.getKey()).getHtmlTitle());
					for (String str : pairs.getValue()) {
						sb.append(activeTags.get(pairs.getKey()).getHtml(str));
					}
				} else {
					sb.append("<br><font size='3'><b>" + pairs.getKey() + ":</font></b>");
					for (String str : pairs.getValue()) {
						sb.append("<br><font size='2'>&nbsp;&nbsp;&nbsp;&nbsp;" + str + "</font>");
					}
				}
			}
			
			sb.append("<br>");
		}
		sb.append("<br>");
	}
	
	public static DocumentationView getInstance() {
		return instance;
	}

}
