package pa.iscde.documentation.jbdqg;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.documentation.view.DocumentationView;
import pa.iscde.filtersearch.providers.SearchProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * This class is responsible for adding the capability of search in the documentation of the opened files in the editor through the Search Tool
 * 
 * @author Jo&atilde;o Gon&ccedil;alves
 * @version 01.00
 */
public class DocumentationFilterSearch implements SearchProvider {
	
	private Map<String,File> openedFiles = new HashMap<String, File>();
	private Map<String,String> openedFilesDoc = new HashMap<String, String>();
	private PidescoServices pidescoServices;
	private JavaEditorServices javaeditorServices;
	
	public DocumentationFilterSearch() {
		Bundle bundle = FrameworkUtil.getBundle(SearchProvider.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<PidescoServices> serviceReference_pidesco = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(serviceReference_pidesco);
		
		ServiceReference<JavaEditorServices> javaeditorReference_pidesco = context.getServiceReference(JavaEditorServices.class);
		javaeditorServices = context.getService(javaeditorReference_pidesco);
		
		//when a new file is opened in the editor, the documentation for that file is generated to became searchable
		javaeditorServices.addListener(new JavaEditorListener.Adapter() {
			@Override
			public void fileOpened(File file) {
				DocumentationFilterSearch.this.retrieveFileDocumentation(file);
				DocumentationFilterSearch.this.getResults(" ");
			}
		});
		
	}
	
	/**
	 * Method responsible to build the search results
	 * 
	 * The search results correspond to the documentation of the files that are opened
	 * 
	 */
	@Override
	public List<Object> getResults(String text) {
		List<Object> docfilelist = new LinkedList<Object>();
		
		//here it is retrieved all the documentation of all the opened files
		this.retrieveAllFilesDocumentation();
		
		//adds to the search results all the documentation files that match with the text received
		for (Map.Entry<String, File> entry : openedFiles.entrySet())
		{
		  	if(!openedFilesDoc.isEmpty() && openedFilesDoc.get((String) entry.getKey()).contains(text)){
				docfilelist.add((String) entry.getKey());
			}
		}
				
		return docfilelist;
		
	}
	
	/**
	 * Method responsible to add a javadoc icon to each of the files available as search results
	 */
	@Override
	public Image setImage(Object object) {
		if (openedFiles.get((String) object) instanceof File){
			return pidescoServices.getImageFromPlugin("pa.iscde.documentation.jbdqg", "javadoc.gif");
		}
		return null;
	}

	/**
	 * Method responsible to open the documentation view with the documentation of the selected file
	 * 
	 * Only works if the documentation view is available
	 * 
	 */
	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		
		DocumentationView view = DocumentationView.getInstance();
		Field vviewarea;
		try {
			vviewarea = view.getClass().getDeclaredField("viewArea");
			vviewarea.setAccessible(true);
			
			try {
				Composite sviewarea = (Composite) vviewarea.get(view);
				
				if (!sviewarea.isDisposed()){
					if (openedFiles.containsKey((String) object)){			
						javaeditorServices.openFile(openedFiles.get((String) object));
						
						if (view != null){
							pidescoServices.openView("pa.iscde.documentation.docView");
						}
					}
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method responsible for generate all the documentation of each of the opened files
	 * 
	 * Each documentation is added to a map class variable to be available to be searched
	 * 
	 */
	private void retrieveAllFilesDocumentation() {
		
		//gets all the opened files
		for (IWorkbenchWindow wbwindow : PlatformUI.getWorkbench().getWorkbenchWindows()){			
			for (IWorkbenchPage page : wbwindow.getPages()){
				for (IEditorReference refpart : page.getEditorReferences()){

					IEditorPart part = refpart.getEditor(true);
					
					if(part != null) {
						IEditorInput input = part.getEditorInput();
						if(input instanceof FileStoreEditorInput) {
							String path = ((FileStoreEditorInput) input).getURI().getPath();
						
							File pageFile = new File(path);
							
							//if the documentation of the file isn't yet available, it is retrieved and made available
							if (!openedFilesDoc.containsKey(pageFile.getName())){
								this.retrieveFileDocumentation(pageFile);
							}							
						}
					}
				}
			}
		}
	}
	
	/**
	 * Method responsible for generate the documentation of a received file
	 * 
	 * It was used reflection to keep the generated documentation similar to the one generated by the documentation plugin
	 * 
	 */	
	private void retrieveFileDocumentation(File pageFile) {
		
		DocumentationView view = DocumentationView.getInstance();
		
		if (view != null){
			
			Field field;
			Object objectDocumentation = null;
			StringBuilder sb = new StringBuilder();
			try {
				
				Method mastvisitor;
					
					try {
						mastvisitor = view.getClass().getDeclaredMethod("findDocumentation");
						mastvisitor.setAccessible(true);
						
						ASTVisitor visitor;
						try {
							visitor = (ASTVisitor) mastvisitor.invoke(view);
							javaeditorServices.parseFile(pageFile, visitor);
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
						
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				field = view.getClass().getDeclaredField("objectDoc");
				field.setAccessible(true);
						
				try {
					objectDocumentation = field.get(view);
					
					if(objectDocumentation != null){
						
						Method mgetName;
						Method mgetComment;
						Method mgetConstructors;
						Method mgetMethod;
						
						try {
							mgetName = objectDocumentation.getClass().getDeclaredMethod("getName");
							mgetName.setAccessible(true);
							
							mgetComment = objectDocumentation.getClass().getDeclaredMethod("getComment");
							mgetComment.setAccessible(true);
							
							mgetConstructors = objectDocumentation.getClass().getDeclaredMethod("getConstrutors");
							mgetConstructors.setAccessible(true);
							
							mgetMethod = objectDocumentation.getClass().getDeclaredMethod("getMethods");
							mgetMethod.setAccessible(true);
													
							try {
								String name = (String) mgetName.invoke(objectDocumentation);
								sb.append("Classe: " + name + "\n");							
								String comment = (String) mgetComment.invoke(objectDocumentation);
								sb.append(comment + "\n");	
								@SuppressWarnings("unchecked")
								List<Object> constructors = (List<Object>) mgetConstructors.invoke(objectDocumentation);
								if ( !constructors.isEmpty() ) {
									
									for (Object oneconstructor : constructors) {
										
										Method mcgetName  = oneconstructor.getClass().getDeclaredMethod("getName");
										mcgetName.setAccessible(true);
										String cname = (String) mcgetName.invoke(oneconstructor);
										sb.append("Construtor: " + cname + "\n");
										
										Method mcgetSignature  = oneconstructor.getClass().getDeclaredMethod("getSignature");
										mcgetSignature.setAccessible(true);
										String csignature = (String) mcgetSignature.invoke(oneconstructor);
										sb.append(csignature + "\n");	
										
										Method mcgetComment  = oneconstructor.getClass().getDeclaredMethod("getComment");
										mcgetComment.setAccessible(true);
										String ccomment = (String) mcgetComment.invoke(oneconstructor);
										sb.append(ccomment + "\n");	
										
										Method mcgetTags  = oneconstructor.getClass().getDeclaredMethod("getTags");
										mcgetTags.setAccessible(true);
										@SuppressWarnings("unchecked")
										Map<String, List<String>> ctags = (Map<String, List<String>>) mcgetTags.invoke(oneconstructor);
										
										for (Map.Entry<String, List<String>> centry : ctags.entrySet())
										{
											sb.append(centry.getKey() + " " + centry.getValue() + "\n");
										}
										
										
									}
									
								}
								
								@SuppressWarnings("unchecked")
								List<Object> methods = (List<Object>) mgetMethod.invoke(objectDocumentation);
								if ( !methods.isEmpty() ) {
									
									for (Object onemethod : methods) {
										
										Method mmgetName  = onemethod.getClass().getDeclaredMethod("getName");
										mmgetName.setAccessible(true);
										String mname = (String) mmgetName.invoke(onemethod);
										sb.append("M&eacute;todo: " + mname + "\n");
										
										Method mmgetSignature  = onemethod.getClass().getDeclaredMethod("getSignature");
										mmgetSignature.setAccessible(true);
										String msignature = (String) mmgetSignature.invoke(onemethod);
										sb.append(msignature + "\n");	
										
										Method mmgetComment  = onemethod.getClass().getDeclaredMethod("getComment");
										mmgetComment.setAccessible(true);
										String mcomment = (String) mmgetComment.invoke(onemethod);
										sb.append(mcomment + "\n");	
										
										Method mmgetTags  = onemethod.getClass().getDeclaredMethod("getTags");
										mmgetTags.setAccessible(true);
										@SuppressWarnings("unchecked")
										Map<String, List<String>> mtags = (Map<String, List<String>>) mmgetTags.invoke(onemethod);
										
										for (Map.Entry<String, List<String>> mentry : mtags.entrySet())
										{
											sb.append(mentry.getKey() + "=" + mentry.getValue() + "\n");
										}
										
									}
								}
									
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			openedFilesDoc.put(pageFile.getName(), sb.toString());
			openedFiles.put(pageFile.getName(), pageFile);
			
		}
	}
	
}