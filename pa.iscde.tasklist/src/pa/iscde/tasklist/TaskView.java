package pa.iscde.tasklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


public class TaskView implements PidescoView {
	
	public enum TaskType {
		PROJECT, PACKAGE, FILE;
	}
	
	private Table table;
	private Combo combo;
	private JavaEditorServices javaServices;
	private ProjectBrowserServices browserServices;
	private TaskType type;
	private ArrayList<File> files;
	private File rootFile;
	private String fileText;
	private String[] views = {"Project", "Package", "Open File"};
	private File currentFile;
	
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
		
		files = new ArrayList<File>();
		currentFile = null;
	}

	//Create the task view table
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new FillLayout(2));
		
		createComboBox(viewArea);
		createTable(viewArea);
		
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
				if(tmp.substring(2, 6).equals("TODO")){
					
					item = new TableItem(table, SWT.SIMPLE);
					
					item.setText(0, currentFile.getParentFile().getName());
					item.setText(1, currentFile.getName().substring(0, currentFile.getName().indexOf(".")));
					item.setText(3, tmp.substring(7));
					item.setText(4, "Category");
					item.setText(5, "Priority");
				}
		        return true;
			}
			
			@Override
			public boolean visit(BlockComment node) {
				System.out.println("Block Comment");
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
		lineCol.setWidth(35);
		TableColumn textCol = new TableColumn(table, SWT.NONE, 3);
		textCol.setText("Text");
		textCol.setWidth(215);
		TableColumn categoryCol = new TableColumn(table, SWT.NONE, 4);
		categoryCol.setText("Category");
		categoryCol.setWidth(110);
		TableColumn priorityCol = new TableColumn(table, SWT.NONE, 5);
		priorityCol.setText("Priority");
		priorityCol.setWidth(80);
		
		table.setHeaderVisible(true);
		
		table.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println(table.getSelectionIndex());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
	}
	
}