package pa.iscde.tasklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pa.iscde.tasklist.extensibility.Category;
import pa.iscde.tasklist.extensibility.CategoryAction;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskView implements PidescoView {
	
	/**
	 * enum available with the several task types
	 */
	public enum TaskType {
		PROJECT, PACKAGE, FILE;
	}
	
	public static final String EXT_POINT_ID_1 = "pa.iscde.tasklist.category";
	public static final String EXT_POINT_ID_2 = "pa.iscde.tasklist.category_action";
	
	private Table table;
	private Combo combo;
	private JavaEditorServices javaServices;
	private ProjectBrowserServices browserServices;
	private PidescoServices pidescoServices;
	private TaskType type;
	private ArrayList<File> files;
	private File rootFile;
	private String fileText;
	private String[] views = {"Project", "Package", "Open File"};
	private File currentFile;
	private ArrayList<Category> catList;
	private List<TodoLine> todos;
	
	/**
	 * the constructor for this class, where the several ServiceReferences are instantiated
	 */
	public TaskView() {
		this.type = TaskType.PROJECT;
		todos = new ArrayList<TodoLine>();
		
		Bundle bundle = FrameworkUtil.getBundle(TaskView.class);
		BundleContext context  = bundle.getBundleContext();
		
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
		javaServices.addListener(new JavaEditorListener() {
			
			@Override
			public void selectionChanged(File file, String text, int offset, int length) {
			}
			
			@Override
			public void fileSaved(File file) {
				try {
					if(table != null && !table.isDisposed()){
						commentReader();
						populateTable();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void fileOpened(File file) {
				try {
					if(table != null && !table.isDisposed()){
						commentReader();
						populateTable();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void fileClosed(File file) {
			}
		});
		
		ServiceReference<ProjectBrowserServices> ref1 = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref1);
		
		ServiceReference<PidescoServices> ref2 = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(ref2);
		
		catList = new ArrayList<Category>();
		files = new ArrayList<File>();
		currentFile = null;
		
		
	}
	
	/**
	 * creates the view for this class
	 * also saves the categories available to an
	private Category category; array
	 * finally reads the comments to fill the table
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		//viewArea.setLayout(new FillLayout(1));
		viewArea.setLayout(new GridLayout(1, false));
		
		createComboBox(viewArea);
		createTable(viewArea);
		createCategories();
		
		try {
			commentReader();
			populateTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//To outside project classes call, so it can get the results from our table
	public void populateData(){
		try {
			commentReader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Populate our table, to insert the values in the correct position, and refresh's the table
	private void populateTable(){
		table.removeAll();
		
		for (TodoLine todo : todos) {
			TableItem item = new TableItem(table, SWT.SIMPLE);
			item.setText(0, todo.packageName);
			item.setText(1, todo.className);
			item.setText(2, "" + todo.line);
			item.setText(3, todo.text);
			item.setText(4, todo.category.getTag());
			
			if(todo.category.getIcon() != null)
				item.setImage(5, todo.category.getIcon());
			else
				item.setText(5, "no icon available");
		}
		
		
	}
	
	//Reads the comments and refreshes the table, accordingly to the TaskType selected at the moment
	private void commentReader() throws IOException {
		todos.clear();
		files.clear();
		
		switch (type) {
		case PROJECT:
			rootFile = browserServices.getRootPackage().getFile();
			getFiles(rootFile);
			break;
		case FILE:
			rootFile = javaServices.getOpenedFile();
			files.add(rootFile);
			break;
		case PACKAGE:
			rootFile = javaServices.getOpenedFile().getParentFile();
			getFiles(rootFile);
			break;
		}
		
		//Visitor
		ASTVisitor visitor = new ASTVisitor() {
			@SuppressWarnings("unchecked")
			@Override
			public boolean visit(CompilationUnit node) {
				for(Comment c : (List<Comment>)(node.getCommentList())){
					c.accept(this);
					int startLine = node.getLineNumber(c.getStartPosition());
					for(TodoLine todo: todos)
						if(todo.line < 0)
							todo.line = startLine;
				}
				return true;
			}
			
			@Override
			public boolean visit(LineComment node) {
				String tmp = fileText.substring(node.getStartPosition(), node.getStartPosition() + node.getLength());
				String[] splitSpace = tmp.split(" ");
				
				for(Category c: catList){
					String catString = null;
					if(splitSpace[0].substring(2).equals(c.getTag())){
						catString = tmp.substring(splitSpace[0].length() + 1);
					} else if(splitSpace.length > 1 && splitSpace[1].equals(c.getTag())){
						catString = tmp.substring(splitSpace[0].length() + splitSpace[1].length() + 2);
					}
					if(catString != null){
						String packageName = currentFile.getParentFile().getName();
						String className = currentFile.getName().substring(0, currentFile.getName().indexOf("."));
						TodoLine newTodo = new TodoLine(packageName, className, -1, catString, c);
						todos.add(newTodo);
					}
				}
		        return true;
			}
			
			@Override
			public boolean visit(BlockComment node) {
		        return true;
			}
		};
		
		for(File f: files){
			currentFile = f;
			fileText = readFile(f);
			javaServices.parseFile(f, visitor);
		}
	}
	
	//Reads the file given as parameter to a String
	private String readFile(File file) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	    } finally {
	        br.close();
	    }
	    
	    return sb.toString();
	}

	/*
	 * Gets the files from the project to read the comments
	 * If "FILE" -> give javaServices.getOpenedFile
	 * If "PACKAGE" -> give project root
	 * If "PROJECT" -> check everything
	 */
	private ArrayList<File> getFiles(File root){
		
		for(File f: root.listFiles()){
			if(f.isDirectory()){
				if(!(f.getName().equals(".metadata")))
					getFiles(f);
			} else{
				files.add(f);
			}
		}
		return files;
	}
	
	//Creates the combo box
	private void createComboBox(Composite viewArea){
		
		Composite c = new Composite(viewArea, SWT.NONE);
		c.setLayout(new RowLayout());
		
		Label label = new Label(c, SWT.NONE | SWT.HORIZONTAL | SWT.CENTER);
		label.setText("View Type: ");
		
		combo = new Combo(c, SWT.BORDER | SWT.DROP_DOWN | SWT.HORIZONTAL | SWT.CENTER);
		for(String s: views)
			combo.add(s);
		combo.setText("Project");
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (combo.getSelectionIndex()) {
				case 0:
					type = TaskType.PROJECT;
					break;
				case 1:
					type = TaskType.PACKAGE;
					break;
				case 2:
					type = TaskType.FILE;
					break;
				default:
					break;
				}
				
				try {
					commentReader();
					populateTable();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	
	}
	
	//Creates the table
	private void createTable(Composite viewArea){
		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		
		table = new Table(viewArea, SWT.NONE);
		
		TableColumn packageCol = new TableColumn(table, SWT.NONE, 0);
		packageCol.setText("Package");
		packageCol.setWidth(80);
		TableColumn classCol = new TableColumn(table, SWT.NONE, 1);
		classCol.setText("Class");
		classCol.setWidth(130);
		TableColumn lineCol = new TableColumn(table, SWT.NONE, 2);
		lineCol.setText("Line");
		lineCol.setWidth(45);
		TableColumn textCol = new TableColumn(table, SWT.NONE, 3);
		textCol.setText("Text");
		textCol.setWidth(215);
		TableColumn categoryCol = new TableColumn(table, SWT.NONE, 4);
		categoryCol.setText("Category");
		categoryCol.setWidth(75);
		TableColumn iconCol = new TableColumn(table, SWT.NONE, 5);
		iconCol.setText("Icon");
		iconCol.setWidth(90);
		
		table.setHeaderVisible(true);
		
		table.setLayoutData(gridData);
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
				final int selected = table.getSelectionIndex();
				for(File f: getFiles(browserServices.getRootPackage().getFile())){
					if(f.getName().equals(table.getItem(selected).getText(1) + ".java"))
						currentFile = f;
				}
				
				Menu popup = new Menu(table);
				final MenuItem item = new MenuItem(popup, SWT.NONE);
				
				IExtensionRegistry reg = Platform.getExtensionRegistry();
				for(IExtension ext : reg.getExtensionPoint(EXT_POINT_ID_2).getExtensions()){
					
					for(IConfigurationElement member : ext.getConfigurationElements()){
						
						try {
							CategoryAction action = (CategoryAction) member.createExecutableExtension("actionClass");
							String categories = member.getAttribute("categories");
							for(String s: categories.split(",")){
								if(s.equals(table.getItem(selected).getText(4))){
									item.setText("Action " + s + ": " + member.getAttribute("name").toLowerCase());
									item.setData(action);
									popup.setDefaultItem(item);
								}
							}
						} catch (InvalidRegistryObjectException | CoreException e1) {
							e1.printStackTrace();
						}
					}
					
				}
				item.addSelectionListener(new SelectionListener() {
					
					@Override
					public void widgetSelected(SelectionEvent e) {
						if(item.getData() instanceof CategoryAction){
							CategoryAction a = (CategoryAction) item.getData();
							a.executeAction(currentFile, javaServices);
							table.setSelection(selected);
						}
					}
					
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
				popup.setVisible(true);
				
				table.select(table.getSelectionIndex());
				
			}
		});
		
	}

	//Creates category objects, accordingly to the extensions created
	public void createCategories(){
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		
		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_ID_1).getExtensions()){
			
			for(IConfigurationElement member : ext.getConfigurationElements()){
				Image img = null;
				Color c = null;
				
				String tag = member.getAttribute("tag");
				String name = member.getAttribute("name");
				String icon = member.getAttribute("icon");
				if(icon != null){
					Image i = pidescoServices.getImageFromPlugin(ext.getContributor().getName(), icon);
					if(i != null){
						img = i;
					}
				}
				String[] color = member.getAttribute("color").split(",");
				c = new Color(null, Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
				
				Category cat = new Category(tag, name, img, c);
				catList.add(cat);
			}	
		}
	}
	
	//Get a list with the results of the line in our task list table
	public List<TodoLine> getTodos() {
		return todos;
	}
	
	//Intermediate structure class, to save the values that are entered in our task list table, so it can be accessible from outside
	public static class TodoLine{
		public final String packageName;
		public final String className;
		public int line;
		public final String text;
		public final Category category;
		
		public TodoLine(String packageName, String className, int line, String text, Category category) {
			this.packageName = packageName;
			this.className = className;
			this.line = line;
			this.text = text;
			this.category = category;
		}
	}
	
}