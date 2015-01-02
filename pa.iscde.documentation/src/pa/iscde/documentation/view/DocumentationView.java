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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.documentation.extension.IDocumentationExportProvider;
import pa.iscde.documentation.extension.ITagContentProvider;
import pa.iscde.documentation.structure.ConstrutorDoc;
import pa.iscde.documentation.structure.MethodDoc;
import pa.iscde.documentation.structure.ObjectDoc;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * This class is responsible for creating the Documentation View plugin for ISCDE IDE.
 * 
 * @author David Franco & João Gonçalves
 * @version 01.00
 */
public class DocumentationView implements PidescoView {

	// Extensions Points IDs
	private static final String TAG_EXT_POINT_ID = "pa.iscde.documentation.tags";
	private static final String EXP_EXT_POINT_ID = "pa.iscde.documentation.exports";
	
	private static DocumentationView instance;
	
	private Composite viewArea;
	private JavaEditorServices javaServices;

	private Browser browser;
	private Button btnExport;
	private ObjectDoc objectDoc;
	private Map<String, ITagContentProvider> activeTags = new HashMap<String, ITagContentProvider>();
	private Map<String, IDocumentationExportProvider> activeExportToList = new HashMap<String, IDocumentationExportProvider>();
	
	/**
	 * DocumentationView Construtor
	 */
	public DocumentationView() {
		Bundle bundle = FrameworkUtil.getBundle(DocumentationView.class);
		BundleContext context  = bundle.getBundleContext();
		
		ServiceReference<JavaEditorServices> refEditor = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(refEditor);
	}

	/**
	 * Creates Documentation View GUI
	 * 
	 * @param viewArea composite where the contents of the view should be added
	 * @param imageMap map indexing the images contained in the "images" folder of the plugin 
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;

		this.viewArea = viewArea;
		this.viewArea.setLayout(new GridLayout(1, false));
		
		// Add Export Button
		btnExport = new Button(this.viewArea, SWT.PUSH);
		btnExport.setText("Export As...");
		btnExport.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false));
		
		// Add Browser
		browser = new Browser(this.viewArea, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		// Fill the View
		this.fillView();
	}

	/**
	 * Method responsable to fill the Documentation View.
	 * 
	 * Is in here, that we look for extensions points.
	 */
	public void fillView() {
		objectDoc = new ObjectDoc();
		
		this.cleanView();

		if (javaServices.getOpenedFile() != null) {
			btnExport.setEnabled(true);
			
			ASTVisitor visitor = findDocumentation();

			javaServices.parseFile(javaServices.getOpenedFile(), visitor);

			// Get and Write all of the tags, but if caught an extension tag, uses that one
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			for (IExtension ext : reg.getExtensionPoint(TAG_EXT_POINT_ID).getExtensions()) {
				for (IConfigurationElement member : ext.getConfigurationElements()) {
					try {
						String tagName = member.getAttribute("name");
						ITagContentProvider provider = (ITagContentProvider) member.createExecutableExtension("provider");

						activeTags.put("@" + tagName, provider);
					} catch (CoreException e) {
						MessageBox messageBox = new MessageBox(viewArea.getShell(), SWT.ICON_ERROR | SWT.OK);
						messageBox.setText("Error loading extension points!");
						messageBox.open();
					}
				}
			}

			// Get and Write all of the exportations
			for (IExtension ext : reg.getExtensionPoint(EXP_EXT_POINT_ID).getExtensions()) {
				for (IConfigurationElement member : ext.getConfigurationElements()) {
					try {
						IDocumentationExportProvider provider = (IDocumentationExportProvider) member.createExecutableExtension("provider");

						activeExportToList.put(provider.getFilterExtension(), provider);
					} catch (CoreException e) {
						MessageBox messageBox = new MessageBox(viewArea.getShell(), SWT.ICON_ERROR | SWT.OK);
						messageBox.setText("Error loading extension points!");
						messageBox.open();
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
							dialog.setFilterPath(System.getProperty("user.home"));
						    dialog.setFileName("exportTo");
						    
						    int i = 0;
							String[] filterNames = new String[activeExportToList.size()];
							String[] filterExtensions = new String[activeExportToList.size()];

							for (IDocumentationExportProvider provider : activeExportToList.values()) {
								filterNames[i] = provider.getFilterName();
								filterExtensions[i] = provider.getFilterExtension();

								i++;
							}
							dialog.setFilterNames(filterNames); 
						    dialog.setFilterExtensions(filterExtensions);
						    
						    String fullFileName = dialog.open();
						    if (fullFileName != null) {
						    	try {
						    		activeExportToList.get(dialog.getFilterExtensions()[dialog.getFilterIndex()]).saveToFile(fullFileName, objectDoc);
								} catch (Exception ex) {
									MessageBox messageBox = new MessageBox(viewArea.getShell(), SWT.ICON_ERROR | SWT.OK);
									messageBox.setText("Error saving file:\n" + ex.getMessage());
									messageBox.open();
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
		}
	}
	
	/**
	 * Method responsable to clean/reset the Documentation View
	 */
	private void cleanView() {
		if (!btnExport.isDisposed())
			btnExport.setEnabled(false);
		
		if (!browser.isDisposed()) {
			StringBuilder sb = new StringBuilder();
			sb.append("<br><font size='4'><b>Nenhum objeto em contexto!</b></font></br>");
			sb.append("<br>Por favor, abra um ficheiro do Project Explorer...</br>");
	
			browser.setText(sb.toString());
		}
	}
	
	/**
	 * Method responsable to visit every node to get the comments and tags
	 * 
	 * @return ASTVisitor
	 */
	private ASTVisitor findDocumentation() {
		ASTVisitor visitor = new ASTVisitor() {

			@Override
			@SuppressWarnings("unchecked")
			public boolean visit(Javadoc node) {
				// Condition to get properties of the class or interface
				if (node.getParent() instanceof TypeDeclaration) {
					objectDoc.setName(((TypeDeclaration) node.getParent()).getName().getFullyQualifiedName());

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
	
	/**
	 * Produce HTML to show in Documentation View
	 * 
	 * @return HTML to show in Documentation View
	 */
	private String toHTML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<br><font size='6'><b><i>Classe: </i></b>" + objectDoc.getName() + "</font>");
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
	
	/**
	 * Write documentation tags to html to show in Documentation View
	 *  
	 * @param sb - StringBuilder in which will be added the documentation tags
	 * @param itTags - Iterator with the tags information
	 */
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
	
	/**
	 * Retrieves Documentation View
	 * 
	 * @return Documentation View instante
	 */
	public static DocumentationView getInstance() {
		return instance;
	}

}
