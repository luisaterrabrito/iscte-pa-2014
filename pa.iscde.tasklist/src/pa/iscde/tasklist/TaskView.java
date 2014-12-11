package pa.iscde.tasklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
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
import org.eclipse.swt.layout.FillLayout;
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
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TaskView implements PidescoView {
	
	public enum TaskType {
		PROJECT, PACKAGE, FILE;
	}
	
	public static final String EXT_POINT_ID_1 = "pa.iscde.tasklist.category";
	public static final String EXT_POINT_ID_2 = "pa.iscde.tasklist.categoryAction";
	
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
	
	//Constructor for class
	public TaskView() {
		
		this.type = TaskType.PROJECT;
		
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
					if(table != null && !table.isDisposed())
						commentReader();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void fileOpened(File file) {
				try {
					if(table != null && !table.isDisposed())
						commentReader();
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

	//Create the task view table
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new FillLayout(1));
		
		createComboBox(viewArea);
		createTable(viewArea);
		createCategories();
		
		try {
			commentReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Reads the comments and alters the table
	private void commentReader() throws IOException {
		
		files.clear();
		table.removeAll();
		
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
		default:
			break;
		}
		
		//Visitor
		ASTVisitor v = new ASTVisitor() {
			
			TableItem item = null;
			
			@Override
			public boolean visit(CompilationUnit node) {
				for(Comment c : (List<Comment>)(node.getCommentList())){
					c.accept(this);
					if(item != null)
						item.setText(2, "" + node.getLineNumber(c.getStartPosition()));
				}
				return true;
			}
			
			@Override
			public boolean visit(LineComment node) {
				
				String tmp = fileText.substring(node.getStartPosition(), node.getStartPosition() + node.getLength());
				String[] splitSpace = tmp.split(" ");
				
				for(Category c: catList){
					if(splitSpace[0].substring(2).equals(c.getTag())){
						
						item = new TableItem(table, SWT.SIMPLE);
						
						item.setText(0, currentFile.getParentFile().getName());
						item.setText(1, currentFile.getName().substring(0, currentFile.getName().indexOf(".")));
						item.setText(3, tmp.substring(splitSpace[0].length() + 1));
						item.setText(4, c.getName());
						if(c.getIcon() != null)
							item.setImage(5, c.getIcon());
						else
							item.setText(5, "no icon available");
						
					} else {
						if(splitSpace.length > 1 && splitSpace[1].equals(c.getTag())){
							
							item = new TableItem(table, SWT.SIMPLE);
							
							item.setText(0, currentFile.getParentFile().getName());
							item.setText(1, currentFile.getName().substring(0, currentFile.getName().indexOf(".")));
							item.setText(3, tmp.substring(splitSpace[0].length() + splitSpace[1].length() + 2));
							item.setText(4, c.getName());
							if(c.getIcon() != null)
								item.setImage(5, c.getIcon());
							else
								item.setText(5, "no icon available");
							
						}
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
			javaServices.parseFile(f, v);
		}
	}
	
	//Function to read the file to a String
	private String readFile(File file) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
	    try {
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	    } finally {
	        br.close();
	    }
	    
	    return sb.toString();
	}

	/*Gets the files from the project to read the comments
	If open file -> give javaServices.getOpenedFile
	If package -> give project root
	If all files for the project -> check everything*/
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
		
		Label label = new Label(c, SWT.NONE | SWT.HORIZONTAL);
		label.setText("View Type: ");
		
		combo = new Combo(c, SWT.BORDER | SWT.DROP_DOWN | SWT.HORIZONTAL);
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
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				ArrayList<File> tmp = getFiles(browserServices.getRootPackage().getFile());
				int selected = table.getSelectionIndex();
				for(int i = 0; i < tmp.size(); i++){
					if(tmp.get(i).getName().equals(table.getItem(selected).getText(1) + ".java")){
						javaServices.openFile(tmp.get(i));
						table.select(selected);
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
				Menu popup = new Menu(table);
				MenuItem item = new MenuItem(popup, SWT.NONE);
				
//				IExtensionRegistry reg = Platform.getExtensionRegistry();
//				for(IExtension ext : reg.getExtensionPoint(EXT_POINT_ID_2).getExtensions()){
//					
//					for(IConfigurationElement member : ext.getConfigurationElements()){
//						String tag = member.getAttribute("tag");
//						
//						item.setText("Popup shown");
//						popup.setDefaultItem(item);
//					}
//					
//				}
				
				item.setText(table.getItem(table.getSelectionIndex()).getText(1));
				popup.setDefaultItem(item);
				
				popup.setVisible(true);
				
				table.select(table.getSelectionIndex());
				
			}
		});
		
	}

	//Creates category objects, accordingly to the extensions created
	private void createCategories(){
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
	
}