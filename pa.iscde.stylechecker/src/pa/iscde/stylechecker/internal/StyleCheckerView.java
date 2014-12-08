package pa.iscde.stylechecker.internal;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pa.iscde.stylechecker.domain.ProjectStyleChecker;
import pa.iscde.stylechecker.domain.StyleCheckerASTVisitor;
import pa.iscde.stylechecker.model.ui.StyleRuleTableView;
import pa.iscde.stylechecker.sipke.DummyRule;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;




public class StyleCheckerView  implements PidescoView {
	
	

	
	public StyleCheckerView() {
	
	}
	
	
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		StyleRuleTableView tbRules = new StyleRuleTableView(viewArea, SWT.FILL);
		
			tbRules.addRule(new DummyRule("Variable Declaraction Rule", 1, true),"VariableDeclarationStatementRule");
			tbRules.addRule(new DummyRule("Import Stament Rule", 3, true),"IImportStatementRule");
			tbRules.addRule(new DummyRule("Try-Catch Stament Rule", 1, true),"ITryCatchStatementRule");
			tbRules.addRule(new DummyRule("Dummy Rule XPTO", 1, true),"IDummyRule");
			setDummyWarnings();
			
			ProjectStyleChecker checker = new ProjectStyleChecker(new StyleCheckerASTVisitor());
			checker.checkWorkSpace();
			StyleCheckerASTVisitor visitor = checker.getVisitor();
			List<ImportDeclaration> importDeclarations = visitor.getImportDeclarations();
			
			for (ImportDeclaration importDeclaration : importDeclarations) {
				
			}
			
	}
		
//		viewArea.setLayout(new FillLayout());
//		Button btn = new Button(viewArea, SWT.NONE);
//		btn.addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				testeWaring();
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		TableViewer viewer = new TableViewer(viewArea, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.CHECK); 
//	    Table table = viewer.getTable();
//	    table.setLinesVisible(true);
//	  
//	    table.setHeaderVisible(true);
//	    Random random = new Random(10);
//	    for (String title : TABLE_HEADER) {
//			TableColumn colmn = new TableColumn(table, SWT.FILL);
//			
//			colmn.setText(title);
//		}
//	    
//	    for (int i = 0; i < 12; i++) {
//	      TableItem item = new TableItem(table, SWT.FILL);
//	      	  item.setText(0, i%2==0?" Started ":" Stopped ");
//	    	  item.setText(1,"      Rule      " + i);
//	    	  item.setText(2, "   Code Style  ");
//	    	  item.setText(3,""+random.nextInt(10));
//	    	  
//	    }
//	    
//	    
//	    
//	    
//	    for (int i = 0; i < TABLE_HEADER.length; i++) {
//	        table.getColumn(i).pack();
//	      }
//	      table.setSize(table.computeSize(SWT.FILL, 3000));
//	    table.setSize(100, 100);
//	  }
//	
	

//	public void updateView (IStyleRule ... rules) {
//		for (IStyleRule iStyleRule : rules) {
//			
//		}
//		
//	}
	
	
	public void setDummyWarnings() {
//		JavaEditorServices editorServices = JavaEditorActivator.getInstance().getServices();
//		File openedFile = editorServices.getOpenedFile();
//		
//		editorServices.addAnnotation(openedFile, AnnotationType.WARNING, "\nWildcard imports usage can be dangerous \n Use explict imports \n", 38, 19);
//		
//		editorServices.addAnnotation(openedFile, AnnotationType.WARNING, "\nMutiple imports statement \n Use one import statment per line \n ", 60, 51);
//		
//		editorServices.addAnnotation(openedFile, AnnotationType.WARNING, "  Dummy ", 113, 27);
//		
//		editorServices.addAnnotation(openedFile, AnnotationType.WARNING, "      Multipe variables declaration \n Use one viriable declaration per statement               ", 170, 26);
//
//		editorServices.addAnnotation(openedFile, AnnotationType.WARNING, "       Dummy Warning             ", 273, 22);
//
//		editorServices.addAnnotation(openedFile, AnnotationType.WARNING, "       Dummy Warning             ", 344, 19);

			}	

}
