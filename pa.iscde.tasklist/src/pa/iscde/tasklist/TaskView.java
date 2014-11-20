package pa.iscde.tasklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class TaskView implements PidescoView {

	private static TaskView instance = new TaskView();
	
	public static TaskView getInstance() {
		return instance;
	}
	
	private Table table;
	private JavaEditorServices javaServices;
	
	public TaskView() {
		Bundle bundle = FrameworkUtil.getBundle(TaskView.class);
		BundleContext context  = bundle.getBundleContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		javaServices = context.getService(ref);
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		table = new Table(viewArea, SWT.NONE);
		TableColumn priorityCol = new TableColumn(table, SWT.NONE);
		priorityCol.setText("Priority");
		priorityCol.setWidth(100);
		TableColumn textCol = new TableColumn(table, SWT.NONE);
		textCol.setText("Text");
		textCol.setWidth(100);
		TableColumn classCol = new TableColumn(table, SWT.NONE);
		classCol.setText("Class");
		classCol.setWidth(100);
		TableColumn lineCol = new TableColumn(table, SWT.NONE);
		lineCol.setText("Line");
		lineCol.setWidth(100);
		
		table.setHeaderVisible(true);
		
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		
		try {
			commentReader();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void commentReader() throws IOException {
		final String fileText = readFile(javaServices.getOpenedFile());
		
		//Visitor
		ASTVisitor v = new ASTVisitor() {
			
			@Override
			public boolean visit(CompilationUnit node) {
				for(Comment c : (List<Comment>)(node.getCommentList())){
					c.accept(this);
				}
				return true;
			}
			
			@Override
			public boolean visit(LineComment node) {
				String tmp = fileText.substring(node.getStartPosition(), node.getStartPosition() + node.getLength());
				if(tmp.substring(2, 6).equals("TODO")){
					System.out.println(tmp);
					
					TableItem item1 = new TableItem(table, SWT.SIMPLE, 0);
					item1.setText("1");
				}
		        return true;
			}
			
			@Override
			public boolean visit(BlockComment node) {
		        return true;
			}
		};
		javaServices.parseFile(javaServices.getOpenedFile(), v);
	}
	
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
}
